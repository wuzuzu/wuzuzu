import axios from 'axios';

const pg = {
    kakao: 'kakaopay'
}

const pay_method = {
    card: 'card'
}

export const requestPay = async ({user ,salePost, count}) => {
    const {IMP} = window;
    IMP.init(`${process.env.REACT_APP_IMP}`);

    IMP.request_pay({
        pg: pg.kakao,
        pay_method: pay_method.card,
        merchant_uid: new Date().getTime(),
        name: salePost.goods,
        amount: salePost.price * count,
        buyer_email: user.email,
        buyer_name: user.userName,
    }, async (rsp) => {
        try {
            const {data} = await axios.post(
                'http://localhost:8080/verify-iamport/' + rsp.imp_uid);
            if (rsp.paid_amount === data.response.amount) {
                alert('결제 성공');
            } else {
                alert('결제 실패');
            }
        } catch (error) {
            console.error('Error while verifying payment:', error);
            alert('결제 실패');
        }
    });
};