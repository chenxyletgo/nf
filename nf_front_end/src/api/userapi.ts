import service from "./base";

const base = '/cliservice/user';

export const registerB = (data: any) => service({
    url: base + '/register',
    method: 'post',
    data,
});

export const sendEmailCodeB = (email: string) => service({
    url: base + '/sendEmailCode',
    method: 'post',
    data: {
        email,
    },
});

export const loginB = (data: any) => service({
    url: base + '/login',
    method: 'post',
    data,
});

// 该方法不是发送请求的
export const getCheckCodeUrlB = () => {
    return service.defaults.baseURL + base + '/getCheckCode?t=' + new Date().getTime();
}

export const getUserB = () => service({
    url: base + '/log/getUserInfo',
    method: 'get',
});

export const editUserB = (data: any) => service({
    url: base + '/log/editUserInfo',
    method: 'put',
    data,
});

export const getUserStateB = () => service({
    url: base + '/getUserState',
    method: 'get',
});

export const getUserDisplay = (id: string) => service({
    
});
