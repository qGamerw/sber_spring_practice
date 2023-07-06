import {useDispatch, useSelector} from "react-redux";
import {Avatar, Badge, Button, Descriptions, Form, Input, message, Modal, Table} from "antd";
import React, {useEffect, useState} from "react";
import userService from "../services/UserService";
import basketService from "../services/BasketService";
import payService from "../services/Payment";
import logo from '../img/censored.png';

export const Registration = () => {
    const [loading, setLoading] = useState(false);
    const [open, setOpen] = useState(false);
    const [isClientLogin, setIsClientLogin] = useState(true);

    const handleModal = (action) => {
        if (action === 'open') {
            setOpen(true);
        } else if (action === 'ok') {
            setLoading(true);

            setTimeout(() => {
                setLoading(false);
                setOpen(false);
                setIsClientLogin(!isClientLogin);
            }, 3000);
        } else if (action === 'cancel') {
            setOpen(false);
        }
    };

    const onFinish = (values) => {
        console.log('Success:', values);
    };

    return (
        <>
            <Button type="primary" onClick={() => handleModal('open')}>
                {isClientLogin ? 'Войти' : 'Логин, Ван-сама'}
            </Button>

            <Modal
                open={open}
                title={isClientLogin ? 'Добро пожаловать в клуб настоящих мужчин' : 'После нажатия кнопки ты станешь обычным человеко'}
                onOk={() => handleModal('ok')}
                onCancel={() => handleModal('cancel')}
                footer={isClientLogin ? [] : [
                    <Button key="back" onClick={() => handleModal('cancel')}>
                        {isClientLogin ? 'Предать свою волю' : 'Нажми меня, чтобы очистить грех'}
                    </Button>,
                    <Button key="submit" type="primary" loading={loading} onClick={() => handleModal('ok')}>
                        {isClientLogin ? 'Присоединиться' : 'Предать свою волю'}
                    </Button>,
                ]}
            >
                <Form
                    name="basic"
                    labelCol={{
                        span: 8,
                    }}
                    wrapperCol={{
                        span: 16,
                    }}
                    style={{
                        maxWidth: 600,
                    }}
                    onFinish={onFinish}
                    autoComplete="off"
                >


                    {isClientLogin ?
                        <>
                            <Form.Item
                                label="Username"
                                name="username"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please input your username!',
                                    },
                                ]}
                            >
                                <Input/>
                            </Form.Item>
                            <Form.Item
                                label="Password"
                                name="password"
                                rules={[
                                    {
                                        required: true,
                                        message: 'Please input your password!',
                                    },]}
                            > <Input.Password/>
                            </Form.Item>
                        </>
                        :
                        ""
                    }


                    {isClientLogin && (
                        <Form.Item
                            wrapperCol={{
                                offset: 8,
                                span: 16,
                            }}
                        >
                            <Button type="primary" htmlType="submit" loading={loading}
                                    onClick={() => handleModal('ok')}>
                                Присоединиться
                            </Button>
                        </Form.Item>
                    )}
                </Form>
            </Modal>
        </>
    );
};
const GetClient = () => {
    const client = useSelector((state) => state.user.user);
    const dispatch = useDispatch();


    useEffect(() => {
        userService.getUser(1, dispatch);
    }, []);

    console.log(client)

    let totalPrice = client.price;

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

                <div style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                    <Button type="primary" style={{display: 'flex'}} onClick={() =>
                        basketService.updateProduct(1, product.id, product.amount + 1, dispatch)}>
                        <a>+</a>
                    </Button>
                    <Button type="primary" style={{display: 'flex'}} onClick={product.amount > 0 ?
                        () => basketService.updateProduct(1, product.id, product.amount - 1, dispatch) : null}>
                        <a>-</a>
                    </Button> <br/>
                    <Button type="primary" onClick={() => basketService.deleteProduct(1, product.id, dispatch)}>
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
                <Descriptions.Item label="Имя">Ван Даркхолм</Descriptions.Item>
                <Descriptions.Item label="Телефон">1810000000</Descriptions.Item>
                <Descriptions.Item label="Email">{client.email}</Descriptions.Item>
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
            <br/>
            <Descriptions title="Информаци о заказе" bordered>
                <Descriptions.Item label="Оплата">Ожидает оплаты</Descriptions.Item>
                <Descriptions.Item label="Время заказа">2018-04-24 18:00:00</Descriptions.Item>
                <Descriptions.Item label="Статус" span={3}> {({totalPrice} > 0) ?
                    <Badge status="processing" text="Ожидание"/> :
                    <Badge status="success" text="В пути"/>}</Descriptions.Item>
                <Descriptions.Item label="Итог">{totalPrice} GCoins</Descriptions.Item>
                {/*<Descriptions.Item label="Количество товара">totalAmount</Descriptions.Item>*/}
            </Descriptions>
            <br/>
            <Table rowKey="id" columns={columns} dataSource={client.products}/>
        </>
    );
}
export const Clients = () => {
    const dispatch = useDispatch();

    const handleButtonClick = () => {
        payService.pay(1, dispatch);

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