import service from "./base";

const base = '/manservice/article';

export const getAticleListA = (data: any, cur: number, size: number) => service({
    url: base + '/adm/getArticleList/' + cur + '/' + size,
    method: 'post',
    data,
});

export const delArticleA = (id: string) => service({
    url: base + '/adm',
    method: 'delete',
    data: {
        id,
    },
});

export const setLevelA = (id: string, msg: number) => service({
    url: base + '/adm/setArticleLevel/' + msg,
    method: 'put',
    data: {
        id,
    },
});

export const setStateA = (id: string, msg: number, reason?: string) => service({
    url: base + "/adm/setArticleState/" + msg,
    method: 'put',
    data: {
        id,
        reason,
    },
});

export const getArticleByIdA = (id: string) => service({
    url: base + '/adm/' + id,
    method: 'get',
});