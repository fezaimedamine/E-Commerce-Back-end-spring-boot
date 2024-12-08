package com.SpringBootProject.proj1.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.SpringBootProject.enums.OrderStatus;
import com.SpringBootProject.proj1.Entitys.CartItems;
import com.SpringBootProject.proj1.Entitys.Coupon;
import com.SpringBootProject.proj1.Entitys.Order;
import com.SpringBootProject.proj1.Entitys.Product;
import com.SpringBootProject.proj1.Entitys.User;
import com.SpringBootProject.proj1.Exception.ValidationException;
import com.SpringBootProject.proj1.Repositry.CartItemsRepositry;
import com.SpringBootProject.proj1.Repositry.CouponRepository;
import com.SpringBootProject.proj1.Repositry.OrderRepository;
import com.SpringBootProject.proj1.Repositry.ProductRepository;
import com.SpringBootProject.proj1.Repositry.UserRepository;
import com.SpringBootProject.proj1.dto.AddProductInCartDto;
import com.SpringBootProject.proj1.dto.CartItemsDto;
import com.SpringBootProject.proj1.dto.OrderDto;
import com.SpringBootProject.proj1.dto.PlaceOrderDto;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CartServiceImp implements CartService {
    
    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CouponRepository couponRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CartItemsRepositry cartItemsRepository;
    
    @Override
    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItems> optionalCartItems = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId());

        if (optionalCartItems.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already in cart");
        } else {
            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                CartItems cartItem = new CartItems();
                cartItem.setProduct(optionalProduct.get());
                cartItem.setPrice(optionalProduct.get().getPrice());
                cartItem.setQuantity(1L);
                cartItem.setUser(optionalUser.get());
                cartItem.setOrder(activeOrder);

                CartItems updatedCartItem = cartItemsRepository.save(cartItem);
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItem.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItem.getPrice());
                activeOrder.getCartItems().add(cartItem);
                orderRepository.save(activeOrder);

                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCartItem);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or product not found");
            }
        }
    }

    @Override
    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
        List<CartItemsDto> cartItemsDtoList = activeOrder.getCartItems()
                .stream()
                .map(CartItems::getCartDto)
                .collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setDiscount(activeOrder.getDiscount());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);
        if(activeOrder.getCoupon()!=null){
            orderDto.setCouponName(activeOrder.getCoupon().getName());
        }
        return orderDto;
    }
    public OrderDto applyCoupon(Long userId, String code) {
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);
    Coupon coupon = couponRepository.findByCode(code).orElseThrow(()-> new ValidationException("Coupon not found."));
    if(couponIsExpired(coupon)){
        throw new ValidationException("Coupon has expired.");
    }
    double discountAmount = ((coupon.getDiscount() / 100.0) * activeOrder.getTotalAmount());
    double netAmount = activeOrder.getTotalAmount() - discountAmount;
    activeOrder.setAmount((long) netAmount);
    activeOrder.setDiscount((long)discountAmount);
    activeOrder.setCoupon(coupon);
    orderRepository.save(activeOrder);
    return activeOrder.getOrderDto();
}
private boolean couponIsExpired(Coupon coupon) {
    Date currentdate = new Date();
    Date expirationDate = coupon.getExpirationDate();
    return expirationDate != null && currentdate.after(expirationDate);
}
public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
    Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

    Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
        addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
    );

    if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
        CartItems cartItem = optionalCartItem.get();
        Product product = optionalProduct.get();

        activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
        activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());
        cartItem.setQuantity(cartItem.getQuantity() - 1);

        if(activeOrder.getCoupon() != null){
            double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
            double netAmount = activeOrder.getTotalAmount() - discountAmount;
            activeOrder.setAmount((long) netAmount);
            activeOrder.setDiscount((long)discountAmount);
        }
        cartItemsRepository.save(cartItem);
    orderRepository.save(activeOrder);
    return activeOrder.getOrderDto();
    }
    return null;
    
}
public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
    Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
    Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());

    Optional<CartItems> optionalCartItem = cartItemsRepository.findByProductIdAndOrderIdAndUserId(
        addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
    );

    if(optionalProduct.isPresent() && optionalCartItem.isPresent()){
        CartItems cartItem = optionalCartItem.get();
        Product product = optionalProduct.get();

        activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
        activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        if(activeOrder.getCoupon() != null){
            double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.0) * activeOrder.getTotalAmount());
            double netAmount = activeOrder.getTotalAmount() - discountAmount;
            activeOrder.setAmount((long) netAmount);
            activeOrder.setDiscount((long)discountAmount);
        }
        cartItemsRepository.save(cartItem);
    orderRepository.save(activeOrder);
    return activeOrder.getOrderDto();
    }
    return null;
    
}

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto){
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if(optionalUser.isPresent()){
        activeOrder.setAddress (placeOrderDto.getAddress());
        activeOrder.setDate((java.sql.Date) new Date());
        activeOrder.setOrderStatus (OrderStatus.Placed);
        activeOrder.setTrackingId(UUID.randomUUID());
        orderRepository.save(activeOrder);
        Order order = new Order();
        order.setAmount (0L);
        order.setTotalAmount (0L);
        order.setDiscount(0L);
        order.setUser (optionalUser.get());
        order.setOrderStatus (OrderStatus.Pending);
        orderRepository.save(order);
        return activeOrder.getOrderDto();
        }
        return null;
    }
    public List<OrderDto> getMyPlaceOrders(Long userId){
        return orderRepository.findByUserIdAndOrderStatusIn(userId, List.of(OrderStatus.Placed,OrderStatus.Shipped,OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }
}
