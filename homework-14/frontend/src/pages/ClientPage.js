import {useDispatch, useSelector} from "react-redux";
import {Avatar, Badge, Button, Descriptions, message, Table} from "antd";
import React, {useEffect} from "react";
import logo from '../imgs/avatarjpg.jpg';
import cartService from "../services/cartService";
import CartService from "../services/cartService";
import payService from "../services/paymentService";

export let isPay = true;

const GetClient = () => {
    const client = useSelector((state) => state.auth.user);
    const cartItems = useSelector((state) => state.cart.items);
    const dispatch = useDispatch();

    const getTotalPrice = () => {
        return cartItems.reduce((total, item) => {
            return total + item.price * item.amount;
        }, 0);
    };

    isPay = getTotalPrice() > 0;
    const getTotalAmount = () => {
        return cartItems.reduce((total, item) => {
            return total + item.amount;
        }, 0);
    };

    useEffect(() => {
        cartService.getBasket(client.id, dispatch);
    }, []);

    const columns = [
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Цена',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Количество',
            dataIndex: 'amount',
            key: 'amount',
        },
        {
            title: '',
            dataIndex: 'amountDic',
            key: 'amountDic',
            render: (_, product) => (

                <div style={{display: 'flex'}}>
                    <Button type="primary" style={{display: 'flex'}}
                            onClick={() => CartService.updateAmount(client.id,
                                product.id, {amount: product.amount + 1}, dispatch)}
                    >
                        <a>+</a>
                    </Button>
                    <Button type="primary" style={{display: 'flex', marginLeft: 10, marginRight: 10}}
                            onClick={product.amount > 0 ?
                                () => CartService.updateAmount(client.id, product.id, {amount: product.amount - 1}, dispatch) : null}
                    >
                        <a>-</a>
                    </Button> <br/>
                    <Button type="primary"
                            onClick={() => CartService.deleteProduct(client.id, product.id, dispatch)}>
                        <a>Удалить</a>
                    </Button>
                </div>
            ),
        }
    ];

    return (
        <>
            <Avatar
                size={{
                    xs: 24 * 2,
                    sm: 32 * 2,
                    md: 40 * 2,
                    lg: 64 * 2,
                    xl: 80 * 2,
                    xxl: 100 * 2,
                }}
                src={logo}
                alt={"Van"}
                style={{marginLeft: 10, marginTop: -20}}
            />
            <Descriptions title="Информация о клиенте">
                <Descriptions.Item label="Имя">{client.username} "Ван Даркхолм"</Descriptions.Item>
                <Descriptions.Item label="Телефон">1810000000</Descriptions.Item>
                <Descriptions.Item label="Email" span={3}>{client.email}</Descriptions.Item>
                <Descriptions.Item label="О себе">Также известный как <br/>
                    TDN Косуги (TDNコスギ),
                    Ван-сама (VAN様),<br/>
                    Тёмная фея (闇の妖精),<br/>
                    М@СТЕР БОНДАЖА (ボンデージマスター),<br/>
                    Тонг Дарк Вэй (佟 dark 为),<br/>
                    Мастер бондажа<br/>
                </Descriptions.Item>
                <Descriptions.Item label="Регион доставки">Вьетнам</Descriptions.Item>
                <Descriptions.Item label="Адресс">
                    No. 18, Wantang Road, Xihu District, Hangzhou, Zhejiang, Vietnam
                </Descriptions.Item>
                <Descriptions.Item label="Режим доступа аккаунта">
                    {client.roles}
                </Descriptions.Item>
            </Descriptions>
            <br/>
            <Descriptions title="Информаци о заказе" bordered>
                <Descriptions.Item label="Оплата">
                    {isPay > 0 ? <Badge status="processing" text="Ожидает оплаты"/> :
                        <Badge status="success" text="Оплачено"/>}
                </Descriptions.Item>
                <Descriptions.Item label="Время заказа" span={3}>2018-04-24 18:00:00</Descriptions.Item>
                <Descriptions.Item label="Итог">{getTotalPrice()} GCoins</Descriptions.Item>
                <Descriptions.Item label="Количество товара">{getTotalAmount()}</Descriptions.Item>
            </Descriptions>
            <br/>
            <Table rowKey="id" columns={columns} dataSource={cartItems}/>
        </>
    );
}
export const Clients = () => {
    const dispatch = useDispatch();
    const client = useSelector((state) => state.auth.user);
    const cartItems = useSelector((state) => state.cart.items);

    const handleButtonClick = () => {
        payService.pay({cardNumber: 143456, userId: client.id}, dispatch);
        cartService.getBasket(client.id, dispatch)
        const getTotalPrice = () => {
            return cartItems.reduce((total, item) => {
                return total + item.price * item.amount;
            }, 0);
        };

        const getTotalAmount = () => {
            return cartItems.reduce((total, item) => {
                return total + item.amount;
            }, 0);
        };

        isPay = false;

        setTimeout(() => {
            message.success(`Оплатили ${getTotalPrice()} товар(а) на ${getTotalAmount()}`, 2);
        }, 100);
    };

    useEffect(() => {
        cartService.getBasket(client.id, dispatch);
    }, []);

    return (
        <>
            <GetClient/>
            <br/>
            <Button type="primary" onClick={handleButtonClick} disabled={!isPay}>Оплата</Button>
        </>
    );
}

export default Clients;