import service from "./base";

const base = '/cliservice/article';

export const getArticleDivListB = (data: any, cur: number, size: number) => service({
    url: base + '/getArticleDivList/' + cur + '/' + size,
    method: 'post',
    data,
});

export const getArticleB = (id: string, state: number) => service({
    url: base + '/getArticle/' + state + '/' + id,
    method: 'get',
});

export const postArticleB = (data: any, action: number) => service({
    url: base + '/log/postArticle/' + action,
    method: 'post',
    data,
});

export const getArticleListByUserIdB = (state: number, cur: number, size: number) => service({
    url: base + '/log/getArticleListByUserId/' + state + '/' + cur + '/' + size,
    method: 'get',
}); 

export const delArticleB = (id: string) => service({
    url: base + '/log/delArticleById',
    method: 'delete',
    data: {
        id,
    },
});

export const getAllArticleByUserIdB = () => service({
    url: base + '/log/getAllArticleByUseId',
    method: 'get',
});

export const getTopArticleListB = () => service({
    url: base + '/getTopArticleList',
    method: 'get',
});

export const getArticleListByTitleB = (search: string, sort: number, cur: number, size: number) => service({
    url: base + '/search/' + sort + '/' + cur + '/' + size,
    method: 'post',
    data: {
        title: search,
    }
});

export const getPreviewArticleB = (id: string) => service({
    url: base + '/log/preview/' + id,
    method: 'get',
});