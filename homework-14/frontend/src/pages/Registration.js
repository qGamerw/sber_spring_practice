import {Button, Form, Input, message, Select,} from 'antd';
import authService from "../services/auth.service";

const {} = Select;

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 8,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 16,
        },
    },
};

const tailFormItemLayout = {
    wrapperCol: {
        xs: {
            span: 24,
            offset: 0,
        },
        sm: {
            span: 16,
            offset: 8,
        },
    },
};
const RegistrationPage = () => {
    const [form] = Form.useForm();
    const onFinish = (values) => {
        authService.register(values)

        const handleButtonClick = () => {
            setTimeout(() => {
                message.success('Приветствую, юный cum-ван!', 3);
            }, 100);
        };
        handleButtonClick();
    };

    return (
        <Form
            {...formItemLayout}
            form={form}
            name="register"
            onFinish={onFinish}
            style={{
                maxWidth: 600,
                padding: '20px 50px'
            }}
            scrollToFirstError
        >
            <Form.Item
                name="username"
                label="Логин"
                rules={[
                    {
                        required: true,
                        whitespace: true,
                    },
                ]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="email"
                label="E-mail"
                rules={[
                    {
                        type: 'email',
                        message: 'Введите email!',
                    },
                    {
                        required: true,
                        message: 'Введите email!',
                    },
                ]}
            >
                <Input/>
            </Form.Item>

            <Form.Item
                name="password"
                label="Пароль"
                rules={[
                    {
                        required: true,
                        message: 'Введите пароль!',
                    },
                ]}
                hasFeedback
            >
                <Input.Password/>
            </Form.Item>

            <Form.Item {...tailFormItemLayout}>
                <Button type="primary" htmlType="submit">
                    Зарегистрироваться
                </Button>
            </Form.Item>
        </Form>
    );
};
export default RegistrationPage;