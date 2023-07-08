import {LockOutlined, UserOutlined} from '@ant-design/icons';
import {Button, Form, Input, message} from 'antd';
import {login} from "../slices/authSlice";
import {useDispatch} from "react-redux";
import {useNavigate} from "react-router-dom";
import authService from "../services/auth.service";

const LoginPage = () => {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const onFinish = (values) => {
        authService.login(values).then((user) => {
            console.log(user)
            dispatch(login(user))

            const handleButtonClick = () => {
                setTimeout(() => {
                    message.success('Ооу, ты уже соскучился!', 3);
                }, 100);
            };
            handleButtonClick();

            navigate("/")
        })
    };
    return (
        <Form
            name="normal_login"
            className="login-form"
            onFinish={onFinish}
            style={{padding: '20px 50px'}}
        >
            <Form.Item
                name="username"
                rules={[
                    {
                        required: true,
                        message: 'Введите имя!',
                    },
                ]}
            >
                <Input prefix={<UserOutlined className="site-form-item-icon"/>} placeholder="Username"/>
            </Form.Item>
            <Form.Item
                name="password"
                rules={[
                    {
                        required: true,
                        message: 'Введите пароль!',
                    },
                ]}
            >
                <Input
                    prefix={<LockOutlined className="site-form-item-icon"/>}
                    type="password"
                    placeholder="Password"
                />
            </Form.Item>

            <Form.Item>
                <Button type="primary" htmlType="submit" className="login-form-button">
                    Log in
                </Button>
            </Form.Item>
        </Form>
    );
};
export default LoginPage;

