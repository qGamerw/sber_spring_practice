
import {Products} from "../components/Product";
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import productService from "../services/ProductService";
import SearchProduct from "../search/SearchProduct";

export const ProductsPage = () => {
    const dispatch = useDispatch();
    useEffect(() => {
        productService.getProduct(dispatch);
    }, []);

    return (
        <>
            <div style={{marginTop: -80}}>
                <SearchProduct/>
            </div>
            <div style={{marginTop: 100}}>
                <Products/>
            </div>
        </>
    )
}