package com.sz.service;

import com.sz.entity.Stock;
import com.sz.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Collections;
import java.util.Optional;

@Service
public class StockService {
    @Autowired
    private final StockRepository stockRepository;
    @Autowired
    private final StringRedisTemplate redisTemplate;
    private RedisScript<Long> stockDeductScript;

    public StockService(StockRepository stockRepository, StringRedisTemplate redisTemplate, RedisScript<Long> stockDeductScript) {
        this.stockRepository = stockRepository;
        this.redisTemplate = redisTemplate;
        this.stockDeductScript = stockDeductScript;
    }

    @Resource
    public  void setStockDeductScript(){
        stockDeductScript=new DefaultRedisScript<>(
                "local stock=tonumber(redis.call('GET',KEYS[1]))"+
                        "if stock>=tonumber(ARGV[1]) then"+
                        "redis.call('DECRBY',KEYS[1],ARGV[1])"+
                        "return 1"+
                        "else return 0 end",
                Long.class
        );
    }

   public boolean deductStock(Long productId){
        String redisKey="stock:"+productId;
        Long result=redisTemplate.execute(stockDeductScript, Collections.singletonList(redisKey));
        return result==1&&result!=null;
   }
    @Transactional
    public boolean confirmStock(Long productId) {
        Optional<Stock> stockOptional = stockRepository.findByProductId(productId);
        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            if (stock.getStock() > 0) {
                stock.setStock(stock.getStock() - 1);
                stockRepository.save(stock);
                return true;
            }
        }
        return false;
    }

    /**
     * 订单取消，回滚库存
     */
    @Transactional
    public void rollbackStock(Long productId) {
        redisTemplate.opsForValue().increment("stock:" + productId);
    }
}
