import {useDispatch, useSelector} from "react-redux";
import {dicreaseAmount, increaseAmount, pay, removeBasket} from "../slices/BasketSlices";
import {Avatar, Badge, Button, Descriptions, message, Table} from "antd";

import {useState} from "react";

const GetClient = () => {
    const productBasket = useSelector((state) => state.baskets.baskets);
    const dispatch = useDispatch();

    let totalPrice = productBasket.reduce((acc, item) => acc + +item.price * +item.amount, 0);
    let totalAmount = productBasket.reduce((acc, item) => acc + +item.amount, 0);

    const columns = [
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Price',
            dataIndex: 'price',
            key: 'price',
        },
        {
            title: 'Amount',
            dataIndex: 'amount',
            key: 'amount',
        },
        {
            title: 'Увеличить',
            dataIndex: 'amountDic',
            key: 'amountDic',
            render: (_, product) => (
                <Button type="primary" onClick={() => dispatch(increaseAmount(product))}>
                    <a>Увеличить</a>
                </Button>
            ),
        },
        {
            title: 'Уменьшить',
            dataIndex: 'amountDic',
            key: 'amountDic',
            render: (_, product) => (
                <Button type="primary" onClick={() => dispatch(dicreaseAmount(product))}>
                    <a>Уменьшить</a>
                </Button>
            ),
        },
        {
            title: 'Удалить',
            key: 'delete',
            render: (_, product) => (
                <Button type="primary" onClick={() => dispatch(removeBasket(product.id))}>
                    <a>Удалить</a>
                </Button>
            ),
        },
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
                src='https://image.emojisky.com/974/10189974-middle.png'
                alt={"Van"}
                style={{marginLeft: 10, marginTop: -20}}
            />
            <Descriptions title="Информация о клиенте">
                <Descriptions.Item label="Имя">Ван Даркхолм</Descriptions.Item>
                <Descriptions.Item label="Телефон">1810000000</Descriptions.Item>
                <Descriptions.Item label="Регион доставки">Вьетнам</Descriptions.Item>
                <Descriptions.Item label="О себе">Также известный как <br/>
                    TDN Косуги (TDNコスギ),
                    Ван-сама (VAN様),<br/>
                    Тёмная фея (闇の妖精),<br/>
                    М@СТЕР БОНДАЖА (ボンデージマスター),<br/>
                    Тонг Дарк Вэй (佟 dark 为),<br/>
                    Мастер бондажа<br/>
                </Descriptions.Item>
                <Descriptions.Item label="Адресс">
                    No. 18, Wantang Road, Xihu District, Hangzhou, Zhejiang, Vietnam
                </Descriptions.Item>
            </Descriptions>
            <Descriptions title="Информаци о заказе" bordered>
                <Descriptions.Item label="Оплата">Ожидает оплаты</Descriptions.Item>
                <Descriptions.Item label="Время заказа">2018-04-24 18:00:00</Descriptions.Item>
                <Descriptions.Item label="Статус" span={3}> {(totalPrice > 0) ?  <Badge status="processing" text="Ожидание"/> : <Badge status="success" text="В пути"/>}</Descriptions.Item>
                <Descriptions.Item label="Итог">{totalPrice} GCoins</Descriptions.Item>
                <Descriptions.Item label="Количество товара">{totalAmount}</Descriptions.Item>
            </Descriptions>
            <Table rowKey="id" columns={columns} dataSource={productBasket}/>
        </>
    );
}

export const Clients = () => {
    const dispatch = useDispatch();

    const handleButtonClick = () => {
        dispatch(pay());

        setTimeout(() => {
            message.success('Loaded!', 2);
        }, 100);
    };

    return (
        <>
            <GetClient/>
            <br/>
            <Button type="primary" onClick={handleButtonClick}>Оплата</Button>
        </>
    );
}