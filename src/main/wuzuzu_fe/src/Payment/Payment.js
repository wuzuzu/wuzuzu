import {createOrder} from "../api/OrderApi";

const pg = {
    kakao: 'kakaopay'
}

const pay_method = {
    card: 'card'
}

export const requestPay = async ({user, salePost, count, handleBackClick}) => {
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
        if (rsp.success) {
            try {
                const {data} = await createOrder({
                    salePostId: salePost.salePostId,
                    count: count,
                    amount: salePost.price * count
                }, rsp.imp_uid);

                const protocol = window.location.protocol;
                const domainUrl = window.location.port === "" ?
                    window.location.hostname :
                    `${window.location.hostname}:${window.location.port}`;

                const eventSource = await new EventSource(
                    `${protocol}//${domainUrl}/api/v1/orders/${rsp.imp_uid}/sse`);

                eventSource.addEventListener("success", event => {
                    alert(event.data);
                    eventSource.close();
                    handleBackClick();
                })

                eventSource.addEventListener("failure", event => {
                    alert(event.data);
                    eventSource.close();
                })
            } catch (error) {
                console.error('Error while verifying payment:', error);
                alert('결제 실패');
            }
        } else {
            alert('결제 취소');
        }
    });
};