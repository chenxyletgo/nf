import service from "./base";

const base = '/cliservice/test';

export const addLikeAB = (data: any) => service({
    url: base + '/addLikeA',
    method: 'post',
    data,
});

export const addCommentB = (data: any) => service({
    url: base + '/addComment',
    method: 'post',
    data,
});

export const getAllUserB = () => service({
    url: base + '/getAllUser',
    method: 'get',
});

export const getAllArticleB = () => service({
    url: base + '/getAllArticle',
    method: 'get',
});
