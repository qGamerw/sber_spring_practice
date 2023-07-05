import {Button, Card, Col, Input, Row} from 'antd';
import {useDispatch, useSelector} from "react-redux";
import {removeProduct} from "../slices/ProductSlices";
import {pushBasket, removeBasket} from "../slices/BasketSlices";

const {Meta} = Card;

const GetProductFindAll = () => {
    const selectProduct = useSelector((state) => state.products.query);
    const allProduct = useSelector((state) => state.products.products);

    const products = selectProduct || allProduct;

    const dispatch = useDispatch()

    return (
        products.map(product => {
            return (
                <Col span={8} key={product.id}>
                    <Card
                        hoverable
                        style={{
                            width: 240,
                            backgroundColor:  "#69c0ff",
                            marginTop: 10
                        }}
                        cover={<img alt={product.name} src={product.url}/>}
                    >
                        <Meta title="Name" description={product.name} /><br/>
                        <Meta title="Price" description={product.price} /><br/>
                        <Meta title="Amount" description={product.amount} /><br/>
                        <Input placeholder="Количество товара" id={product.id} /><br/><br/>
                        <Button
                            type="primary"
                            style={{ marginLeft: 15, marginTop: 10 }}
                            onClick={() => dispatch(pushBasket({product: product, amount: document.getElementById(product.id).value}))}
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
                <GetProductFindAll/>
            </Row>
        </div>
    );
}