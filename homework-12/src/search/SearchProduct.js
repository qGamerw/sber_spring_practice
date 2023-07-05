import {AutoComplete, Input} from 'antd';
import {useState} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {searchProducts} from "../slices/ProductSlices";

const SearchProduct = () => {
    const products = useSelector((state) => state.products.products)
    const dispatch = useDispatch();
    const [options, setOptions] = useState([]);

    const handleSearch = (value) => {
        dispatch(searchProducts(value));
        setOptions(value ? searchResult(value) : []);
    };

    const onSelect = (value) => {
        dispatch(searchProducts(value.toString()));
    };

    const searchResult = (query) => {
        return products
            .filter(product => product.name.toLowerCase().includes(query.toLowerCase()))
            .map(product => {
                return {
                    value: product.name,
                    label: <div key={product.id}>{product.name}</div>
                }
            })
    }

    return (
        <AutoComplete
            popupMatchSelectWidth={252}
            style={{
                width: 300,
                float: "right",
                margin: 10,
                marginTop: 20
            }}
            options={options}
            onSelect={onSelect}
            onSearch={handleSearch}
        >
            <Input.Search size="large" placeholder="Введите здесь" enterButton/>
        </AutoComplete>
    );
};
export default SearchProduct;