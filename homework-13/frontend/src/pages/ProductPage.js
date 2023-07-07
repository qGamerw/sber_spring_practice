import React, {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Button, Card, Col, Input, Row} from "antd";
import CartService from "../services/productBasket";
import ProductService from "../services/productService";

const {Meta} = Card;

const ProductList = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const products = useSelector((state) => state.products.products);
    const dispatch = useDispatch();

    useEffect(() => {
        ProductService.getProducts(dispatch);
    }, []);


    return (
        <div>
            <Row gutter={[16, 16]}>
                {products.map((product) => (
                    <Col span={8} key={product.id}>
                        <Card
                            hoverable
                            style={{
                                width: 240,
                                backgroundColor: "#69c0ff",
                                marginTop: 10
                            }}
                            cover={<img alt={product.name} src={product.url}/>}
                        >
                            <Meta title="Name" description={product.name}/><br/>
                            <Meta title="Price" description={product.price}/><br/>
                            <Meta title="Amount" description={product.amount}/><br/>
                            <Input placeholder="Количество товара" id={product.id}/><br/><br/>
                            <Button
                                type="primary"
                                style={{marginLeft: 15, marginTop: 10}}
                                onClick={() => CartService.addProduct(user.id, product.id, document.getElementById(product.id).value, dispatch)}
                            >
                                Добавить в корзину
                            </Button>
                        </Card>
                    </Col>
                ))}
            </Row>
        </div>
    );
};

export default ProductList;
