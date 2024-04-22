import React from 'react';
import {
    Box,
    Card,
    CardContent,
    CardMedia,
    Chip,
    Divider,
    Grid,
    Typography
} from '@mui/material';

function OrderItem({order}) {
    const totalPrice = order.price * order.count;

    return (
        <Grid item xs={12}>
            <Card sx={{
                borderRadius: "20px",
                boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
                transition: "transform 0.3s",
                "&:hover": {
                    transform: "translateY(-5px)",
                },
            }}>
                {order.image ? (
                    <CardMedia component="img" height="150"
                               image={order.image}
                               alt={order.title}/>
                ) : null}
                <CardContent>
                    <Typography variant="h6"
                                sx={{mb: 1}}>{order.title}</Typography>
                    <Typography variant="body2" color="text.secondary"
                                sx={{mb: 1}}>
                        상품명: {order.goods}
                    </Typography>
                    <Typography variant="body2" color="text.secondary"
                                sx={{mb: 1}}>
                        수량: {order.count}
                    </Typography>
                    <Typography variant="body2" color="text.secondary"
                                sx={{mb: 2}}>
                        가격: {order.price}원
                    </Typography>
                    <Divider sx={{mb: 2}}/>
                    <Box sx={{display: 'flex', alignItems: 'center', mb: 2}}>
                        <Chip label={`판매자: ${order.author}`} variant="outlined"
                              sx={{mr: 2}}/>
                        <Chip label={`카테고리: ${order.category}`}
                              variant="outlined"/>
                    </Box>
                    <Typography variant="body1" sx={{mb: 1}}>
                        주문 번호: {order.orderId}
                    </Typography>
                    <Typography variant="body1" sx={{mb: 1}}>
                        결제 ID: {order.impUid}
                    </Typography>
                    <Typography variant="body1" sx={{mb: 1}}>
                        주문 ID: {order.merchantUid}
                    </Typography>
                    <Box sx={{
                        display: 'flex',
                        justifyContent: 'flex-end',
                        mt: 2
                    }}>
                        <Typography variant="h6" color="primary">
                            총 결제 금액: {totalPrice}원
                        </Typography>
                    </Box>
                </CardContent>
            </Card>
        </Grid>
    );
}

export default OrderItem;