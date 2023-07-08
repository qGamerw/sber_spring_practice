import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Button, Card, Col, Input, message, Row} from "antd";
import ProductService from "../services/productService";
import logo from "../imgs/products.jpg"
import CartService from "../services/cartService";

const {Meta} = Card;

const ProductsPage = () => {
    const user = JSON.parse(localStorage.getItem("user"));
    const allProducts = useSelector((state) => state.products.products);
    const dispatch = useDispatch();
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredProducts, setFilteredProducts] = useState([]);
    const isLoginIn = useSelector((state) => state.auth.isLoggedIn);

    useEffect(() => {
        ProductService.getProducts(dispatch);
    }, []);

    useEffect(() => {
        const filtered = allProducts.filter(product => product.name.toLowerCase().includes(searchTerm.toLowerCase()));
        setFilteredProducts(filtered);
    }, [searchTerm, allProducts]);

    const handleButtonClick = (userId, productId, amount, dispatch) => {
        CartService.addProduct(userId, productId, amount, dispatch);
        setTimeout(() => {
            message.success("Ты успешно добавил продукт в корзину в количестве " + amount + " !", 3);
        }, 100);
    };

    return (
        <div>
            <div style={{ width: '100%', display: 'flex', justifyContent: 'center' }}>
                <Input.Search size="text"
                              value={searchTerm} onChange={(e) => setSearchTerm(e.target.value)}
                              placeholder="Поиск по имени продукта" enterButton
                              className="custom-search-input"
                              style={{ width: '600px' }}
                />
            </div>
            <Row gutter={[16, 16]}>
                {filteredProducts.map((product) => (
                    <Col span={8} key={product.id}>
                        <Card
                            hoverable
                            style={{
                                width: 240,
                                backgroundColor: "#69c0ff",
                                marginTop: 10
                            }}
                            cover={<img alt={product.name} src={logo}/>}
                        >
                            <Meta title="Name" description={product.name}/><br/>
                            <Meta title="Price" description={product.price}/><br/>
                            <Meta title="Amount" description={product.amount}/><br/>
                            {isLoginIn?  <Input placeholder="Количество товара" id={product.id}/> : null} <br/><br/>

                            {isLoginIn?
                                <Button
                                type="primary"
                                style={{marginLeft: 15, marginTop: 10}}
                                onClick={() => handleButtonClick(user.id, product.id, document.getElementById(product.id).value, dispatch)}
                            >
                                Добавить в корзину
                            </Button> : null }
                        </Card>
                    </Col>
                ))}
            </Row>
        </div>
    );
};

export default ProductsPage;
