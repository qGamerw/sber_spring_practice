import {Button, Card, Col, Input, Row} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {clearSearchResults} from "../slices/ProductSlices";
import basketService from "../services/BasketService";

const {Meta} = Card;

const GetProduct = () => {
    const selectProduct = useSelector((state) => state.products.query);
    const allProduct = useSelector((state) => state.products.products);
    const dispatch = useDispatch();

    const products = selectProduct || allProduct;

    useEffect(() => {
        return () => {
            dispatch(clearSearchResults());
        };
    }, [dispatch]);

    return (
        products.map(product => {
            return (
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
                            onClick={() => basketService.add(1, product.id, parseInt(document.getElementById(product.id).value), dispatch)}
                        >
                            Добавить в корзину
                        </Button>
                    </Card>
                </Col>
            );
        })
    );
}


export const Products = () => {
    return (
        <div>
            <Row gutter={16}>
                <GetProduct/>
            </Row>
        </div>
    );
}