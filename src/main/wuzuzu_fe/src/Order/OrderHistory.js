import React from 'react';
import {Box, Button, Grid, Paper, Typography} from '@mui/material';
import {ArrowBack as ArrowBackIcon} from '@mui/icons-material';
import OrderItem from './OrderItem';

function OrderHistory({orders, handleBackClick}) {
    return (
        <Box sx={{
            display: 'flex',
            flexDirection: 'column',
            alignItems: 'center',
            m: 2,
            p: 3,
            backgroundColor: 'rgba(255, 255, 255, 0.8)',
            borderRadius: '20px',
            boxShadow: '0px 4px 10px rgba(0, 0, 0, 0.1)'
        }}>
            <Box sx={{
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                width: '100%',
                mb: 4,
                position: 'relative',
            }}>
                <Button
                    startIcon={<ArrowBackIcon/>}
                    onClick={handleBackClick}
                    sx={{
                        position: 'absolute',
                        left: 0,
                        color: '#4caf50',
                        borderColor: '#4caf50',
                        '&:hover': {
                            backgroundColor: '#4caf50',
                            color: 'white',
                        },
                    }}
                >
                    뒤로 가기
                </Button>
                <Typography variant="h4" component="div" sx={{
                    color: '#4caf50',
                    fontWeight: 'bold',
                    textAlign: 'center',
                    width: '100%',
                }}>
                    주문 내역
                </Typography>
            </Box>
            <Grid container spacing={3} justifyContent="center">
                {orders.map((order) => (
                    <Grid item key={order.orderId} xs={12}>
                        <Paper elevation={3}
                               sx={{borderRadius: '20px', overflow: 'hidden'}}>
                            <OrderItem order={order}/>
                        </Paper>
                    </Grid>
                ))}
            </Grid>
        </Box>
    );
}

export default OrderHistory;