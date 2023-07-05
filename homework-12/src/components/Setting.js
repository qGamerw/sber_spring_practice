import {Button, Card, Col, Form, Input, Row, Select} from 'antd';
import {editProduct, pushProduct, removeProduct} from "../slices/ProductSlices";
import {useDispatch, useSelector} from "react-redux";
import Meta from "antd/es/card/Meta";

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
    const [form] = Form.useForm();
    const dispatch = useDispatch();

    const onFinish = (values) => {
        dispatch(pushProduct(values));
    };
    const onReset = () => {
        form.resetFields();
    };
    const onFill = () => {
        form.setFieldsValue({
            name: 'Самый нужный товар №',
            price: 10,
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
                    label="Name"
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
                    label="Price"
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
                        Добавить
                    </Button>
                    <Button htmlType="button" onClick={onReset}>
                        Reset
                    </Button>
                    <Button type="link" htmlType="button" onClick={onFill}>
                        Fill form
                    </Button>
                </Form.Item>
            </Form>
        </>
    );
};

const GetCart = () => {
    const dispatch = useDispatch();
    const selectProduct = useSelector((state) => state.products.query);
    const allProduct = useSelector((state) => state.products.products);

    const products = selectProduct || allProduct;

    return products.map(product => {
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
                    <Input placeholder="Количество товара" id={product.id}/><br/><br/>
                    <Input placeholder="Количество товара" id={product.id + 1}/><br/><br/>
                    <Button type={"primary"} onClick={() => dispatch(editProduct({
                        id: product.id, name: document.getElementById(product.id).value,
                        price: document.getElementById(product.id + 1).value
                    }))} style={{
                        marginLeft: 45,
                    }}>Изменить</Button>
                    <Button type={"primary"} onClick={() => dispatch(removeProduct(product.id))} style={{
                        marginLeft: 50,
                        marginTop: 10
                    }}>Удалить</Button>
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
                <GetCart/>
            </Row>
        </div>
    );
}