import service from "./base";

const base = '/cliservice/likeA';

export const addLikeAB = (id: string) => service({
    url: base + '/log/addLikeA',
    method: 'put',
    data: {
        id,
    },
});