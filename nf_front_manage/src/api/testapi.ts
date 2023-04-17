import service from "./base";

const base = '/manservice/test';

export const addArticleA = (data: any) => service({
    url: base + '/article',
    method: 'post',
    data,
});

export const getImgA = (url: string) => service({
    url,
    method: 'get',
});