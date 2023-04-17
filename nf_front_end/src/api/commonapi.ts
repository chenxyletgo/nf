import service from "./base";

const base = '/cliservice/common';

export const uploadImgUrl = () => {
    return service.defaults.baseURL + base + '/uploadImg';
}

export const uploadImgB = (file: any) => service({
    url: base + '/uploadImg',
    method: 'post',
    data: file,
});