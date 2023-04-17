import service from "./base";

const base = '/cliservice/category';

export const getFirstCatesB = () => service({
    url: base + '/getFirstCates',
    method: 'get',
});

export const getSecondCateB = (pathName: string) => service({
    url: base + '/getSecondCates/' + pathName,
    method: 'get',
});

export const getTreeCatesB = () => service({
    url: base + '/getTreeCates',
    method: 'get',
});