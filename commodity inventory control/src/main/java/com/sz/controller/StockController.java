package com.sz.controller;

import com.sz.service.OrderProducer;
import com.sz.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//RESTful Web服务核心注解，组合@Controller和@ResponseBody自动将返回对象序列化为JSON/XML
@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired//从容器查找匹配类型的Bean注入
    private StockService stockService;

    @Autowired
    private OrderProducer orderProducer;

    /**
     * 预扣库存
     */
    @PostMapping("/deduct/{productId}")
    public String deductStock(@PathVariable Long productId) {
        boolean success = stockService.deductStock(productId);
        return success ? "扣减成功" : "库存不足";
    }

    /**
     * 确认订单（模拟支付成功后触发消息队列）
     */
    @PostMapping("/confirm/{productId}")
    public String confirmStock(@PathVariable Long productId) {
        orderProducer.sendOrderMessage(productId);
        return "订单已提交，异步扣减库存";
    }

    /**
     * 取消订单（回滚库存）
     */
    @PostMapping("/rollback/{productId}")
    public String rollbackStock(@PathVariable Long productId) {
        stockService.rollbackStock(productId);
        return "库存回滚成功";
    }
}
