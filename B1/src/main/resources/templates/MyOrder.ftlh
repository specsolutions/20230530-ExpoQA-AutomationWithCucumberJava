<#include "layout.ftlh">
<#macro page_title>
    My Order
</#macro>

<#macro page_body>
    <h2>My Order</h2>
    <hr />
    <#if !(authenticationService.isLoggedIn(springMacroRequestContext)) >
    <h3>Please <a href="/Login">Login</a> to see your order!</h3>
    <#else>
    <h3>Order Details</h3>
    <ul>
        <li>Delivery Address: <span class="address" id="deliveryAddress"></span></li>
        <li>Delivery Date: <span class="date" id="DeliveryDate"></span></li>
        <li>Delivery Time: <span class="time" id="DeliveryTime"></span> </li>
    </ul>
    <form action="/OrderDetails" method="get">
    <input type="submit" value="Change" id="ChangeDetailsButton" class="btn btn-default" />
    </form>
    <hr />
    <h3>Ordered Items</h3>
    <ul id="OrderItems"></ul>
    <hr />
    <h4>Subtotal: <span id="SubtotalPrice"></span></h4>
    <h4>Delivery Costs: <span id="DeliveryCosts"></span></h4>
    <h3>Total Price: <span id="TotalPrice"></span></h3>
    <form id="OrderForm">
        <input type="submit" value="Order Now!" id="PlaceOrderButton" class="btn btn-default" />
    </form>
    </#if>
</#macro>

<#macro scripts>
    <#if authenticationService.isLoggedIn(springMacroRequestContext) >
        <script language="javascript">
            $(function() {
                $.ajax('/api/order')
                    .then(
                        function success(order) {
                            console.log(order);
                            $('#deliveryAddress').html(order.deliveryAddress.streetAddress +
                                ', ' +
                                order.deliveryAddress.city +
                                ', ' +
                                order.deliveryAddress.zip);
                            $('#DeliveryDate').html(order.deliveryDate.substring(0, 10)); //yyyy-MM-dd
                            $('#DeliveryTime').html(order.deliveryTime.substring(0, 5)); //h\\:mm
                            $('#SubtotalPrice').html('$' + order.prices.subTotal);
                            $('#DeliveryCosts').html('$' + order.prices.deliveryCosts);
                            $('#TotalPrice').html('$' + order.prices.total);
                            var ul = $("#OrderItems");
                            order.orderItems.forEach(function(orderItem) {
                                var li = $('<li>');
                                li.append($('<span class="order-item-name">').html(orderItem.name));
                                li.append(' (' + orderItem.size + ')');
                                ul.append(li);
                            });
                        },
                        function fail(request) {
                            console.log(request);
                            alert('Request failed (' + request.status + '). ' + request.statusText);
                        }
                    );
                $('#OrderForm').submit(function(event) {
                    $.ajax('/api/order', { method: 'PATCH' })
                        .then(
                            function success() {
                                console.log("ordered");
                                window.location.href = '/';
                            },
                            function fail(request) {
                                console.log(request);
                                alert('Request failed (' + request.status + '). ' + request.statusText);
                            }
                        );
                    // stop the form from submitting the normal way and refreshing the page
                    event.preventDefault();
                });
            });
        </script>
    </#if>


</#macro>

<@display_page/>
