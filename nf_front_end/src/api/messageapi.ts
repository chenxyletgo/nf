import service from "./base";

const base = '/cliservice/message';

export const getMessListB = (cur: number, size: number) => service({
    url: base + '/log/getMessList/' + cur + '/' + size,
    method: 'get',
});