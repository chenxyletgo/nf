import axios from 'axios';
import { ElMessage } from 'element-plus';
import cookie from 'js-cookie';
import { IPaddr } from '../store';

const service = axios.create({
    baseURL: 'http://' + IPaddr + ':7770',
    timeout: 10000,
    withCredentials: true,
});

service.interceptors.request.use(
    // 发送请求前执行的方法
    (config) => {
        // 给请求头添加token字段
        if (cookie.get('nf_token')) config.headers['token'] = cookie.get('nf_token');
        return config;
    },
    // 发送请求失败执行的方法
    (error) => { 
        ElMessage.error('请求发送失败')
    },
);

service.interceptors.response.use(
    // 接收响应后第一个执行的方法
    (response) => {
        if (response.data.code === 20001) {
            ElMessage.error(response.data.message);
            return Promise.reject(new Error(response.data.message));
        } else return response.data;
    },
    // 接收响应失败后执行的方法
    (error) => {
        ElMessage.error('服务器没有响应，请等几秒再试一试。');
    },
);

export default service;