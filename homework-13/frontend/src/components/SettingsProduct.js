import {Button, Card, Col, Form, Input, Row, Select} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import Meta from "antd/es/card/Meta";
import {useEffect} from "react";
import ProductService from "../services/productService";
import CProductService from "../services/productService";
import CartService from "../services/productBasket";

const {Option} = Select;
const layout = {
    labelCol: {
        span: 8,
    },
    wrapperCol: {
        span: 16,
    },
};
const tailLayout = {
    wrapperCol: {
        offset: 8,
        span: 16,
    },
};
export const AddProductForm = () => {
    const [form] = Form.useForm()

    const allProduct = useSelector((state) => state.products.products);
    const dispatch = useDispatch();

    const onFinish = (values) => {
        // productService.createProduct(values, dispatch);
    };
    const onReset = () => {
        form.resetFields();
    };
    const onFill = () => {
        form.setFieldsValue({
            name: 'Самый нужный товар №',
            price: 10,
            amount: 10,
        });
    };


    return (
        <>
            <Form
                {...layout}
                form={form}
                name="control-hooks"
                onFinish={onFinish}
                style={{
                    maxWidth: 600,
                }}
            >
                <Form.Item
                    name="name"
                    label="Название товара"
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="price"
                    label="Цена"
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item
                    name="amount"
                    label="Кличестов"
                    rules={[
                        {
                            required: true,
                        },
                    ]}
                >
                    <Input/>
                </Form.Item>

                <Form.Item {...tailLayout}>
                    <Button type="primary" htmlType="submit">
                        Добавить товар
                    </Button>
                    <Button htmlType="button" onClick={onReset}>
                        Очистить поле ввода
                    </Button>
                    <Button type="link" htmlType="button" onClick={onFill}>
                        Автоматический заполнить поля
                    </Button>
                </Form.Item>
            </Form>
        </>
    );
};

const GetBasket = () => {
    const dispatch = useDispatch();
    const allProduct = useSelector((state) => state.products.products);
    const userId = useSelector((state) => state.auth.user.id);

    useEffect(() => {
        ProductService.getProducts(dispatch);
    }, []);

    return allProduct.map(product => {
        return (
            <Col span={8} key={product.id}>
                <Card
                    hoverable
                    style={{
                        width: 240,
                        backgroundColor: "#69c0ff",
                        marginTop: 10,
                    }}
                    cover={<img alt={product.name} src={product.url}/>}
                >
                    <Meta title="Name"/><p>{product.name}</p>
                    <Meta title="Price"/><p>{product.price}</p>
                    <Meta title="Amount"/><p>{product.amount}</p>
                    <Input placeholder="Имя товара" id={product.id}/><br/><br/>
                    <Input placeholder="Цена товара" id={product.id + 0.1}/><br/><br/>
                    <Input placeholder="Количество товара" id={product.id + 0.2}/><br/><br/>
                    <Button type={"primary"} onClick={() =>
                        CProductService.updateProduct({
                                name: document.getElementById(product.id).value,
                                price: document.getElementById(product.id + 0.1).value,
                                amount: document.getElementById(product.id + 0.2).value
                            },
                            dispatch)} style={{
                        marginLeft: 25,
                    }}>Изменить товар</Button>
                    <Button type={"primary"} onClick={() => CartService.deleteProduct(userId, product.id, dispatch)}
                            style={{
                                marginLeft: 30,
                                marginTop: 10
                            }}>Удалить товар</Button>
                </Card>
            </Col>
        );
    })
}

export const SettingProducts = () => {
    return (
        <div>
            <AddProductForm/>
            <Row>
                <GetBasket/>
            </Row>
        </div>
    );
}