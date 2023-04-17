import service from "./base";

const base = '/manservice/user';

export const login = (data: any) => service({
    url: base + '/login',
    method: 'post',
    data,
});

export const getUserListA = (data: any, cur: number, size: number) => service({
    url: base + '/adm/getUserList/' + cur + '/' + size,
    method: 'post',
    data,
});

export const setStateA = (id: string, msg: number) => service({
    url: base + '/adm/setUserState/' + msg,
    method: 'put',
    data: {
        id,
    },
});

export const delUserA = (id: string) => service({
    url: base + '/adm',
    method: 'delete',
    data: {
        id,
    },
});