package me.longDay.order.service;


import me.longDay.order.mapper.OrderMapper;
import me.longDay.order.pojo.Order;
import me.longDay.order.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;
    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        // 2.利用restTemplate
        String userInfoUrl = "http://user-service/user/" + order.getUserId();
        // 2.2发送http实现远程请求调用
        User userInfo = restTemplate.getForObject(userInfoUrl, User.class);
        // 3.封装User到Order中
        order.setUser(userInfo);
        // 4.返回
        return order;
    }
}
