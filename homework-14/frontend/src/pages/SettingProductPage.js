import {Button, Card, Col, Form, Input, message, Row, Select} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import Meta from "antd/es/card/Meta";
import React, {useEffect, useState} from "react";
import logo from "../imgs/products.jpg"
import ProductService from "../services/productService";
import productService from "../services/productService";

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
    const dispatch = useDispatch();

    const onFinish = (values) => {
        ProductService.createProduct(values, dispatch);
        setTimeout(() => {
            message.success('Ты успешно создал продукт!', 3);
        }, 100);
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

const GetBasket = ({searchTerm}) => {
    const dispatch = useDispatch();
    const allProducts = useSelector((state) => state.products.products);
    const [filteredProducts, setFilteredProducts] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null);

    useEffect(() => {
        productService.getProducts(dispatch);
    }, []);

    const handleButtonClickUpdate = (product, dispatch) => {
        ProductService.updateProduct(product, dispatch);
        setTimeout(() => {
            message.success('Ты успешно обновил продукт!', 3);
        }, 100);
    };

    const handleButtonClickDelete = (productId, dispatch) => {
        productService.deleteProduct(productId, dispatch);
        setTimeout(() => {
            message.success('Ты успешно удалил продукт!', 3);
        }, 100);
    };

    useEffect(() => {
        const filtered = allProducts.filter(product => product.name.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredProducts(filtered);
    }, [searchTerm, allProducts]);

    return filteredProducts.map(product => {
        return (
            <>
                <Col span={8} key={product.id}>
                    <Card
                        hoverable
                        style={{
                            width: 240,
                            backgroundColor: "#69c0ff",
                            marginTop: 10,
                        }}
                        cover={<img alt={product.name} src={logo}/>}
                    >
                        <Meta title="Name"/><p>{product.name}</p>
                        <Meta title="Price"/><p>{product.price}</p>
                        <Meta title="Amount"/><p>{product.amount}</p>
                        <Input placeholder="Имя товара" id={product.id}/><br/><br/>
                        <Input placeholder="Цена товара" id={product.id + 0.1}/><br/><br/>
                        <Input placeholder="Количество товара" id={product.id + 0.2}/><br/><br/>
                        <Button type={"primary"} onClick={() =>
                            handleButtonClickUpdate({
                                    id: product.id,
                                    name: document.getElementById(product.id).value,
                                    price: document.getElementById(product.id + 0.1).value,
                                    amount: document.getElementById(product.id + 0.2).value
                                },
                                dispatch)}
                                style={{marginLeft: 25,}}>
                            Изменить товар</Button>
                        <Button type={"primary"} onClick={() => handleButtonClickDelete(product.id, dispatch)}
                                style={{
                                    marginLeft: 30,
                                    marginTop: 10
                                }}>Удалить товар</Button>
                    </Card>
                </Col>
            </>
        );
    })
}

export const SettingProducts = () => {
    const [searchTerm, setSearchTerm] = useState("");

     return (
        <div>
            <AddProductForm/>
            <div style={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
                <Input.Search size="text"
                              value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)}
                              placeholder="Поиск по имени продукта" enterButton
                              className="custom-search-input"
                              style={{ width: '600px' }}
                />
            </div>
            <br/>
            <Row>
                <GetBasket searchTerm={searchTerm}/>
            </Row>
        </div>
    );
}

export default SettingProducts;