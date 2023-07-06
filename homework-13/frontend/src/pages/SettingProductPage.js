import {SettingProducts} from "../components/Setting";
import {useEffect} from "react";
import productService from "../services/ProductService";
import {useDispatch} from "react-redux";
import SearchProduct from "../search/SearchProduct";
import {Products} from "../components/Product";

export const SettingProductsPage = () => {
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
                <SettingProducts/>
            </div>
        </>
    )
}