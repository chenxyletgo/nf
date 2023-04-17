import service from "./base";

const base = '/cliservice/comment';

export const getCommentListB = (data: any, cur: number, size: number) => service({
    url: base + '/getCommentList/' + cur + '/' + size,
    method: 'post',
    data,
});

export const addCommentB = (data: any) => service({
    url: base + '/log/addComment',
    method: 'post',
    data,
});

export const getCommListByArticleIdB = (id: string, cur: number, size: number) => service({
    url: base + '/log/getCommListByArticleId/' + id + '/' + cur + '/' + size,
    method: 'get',
});

export const delCommentB = (id: string) => service({
    url: base + '/log/delComment',
    method: 'delete',
    data: {
        id,
    },
});
