interface User {
    id: string,
    name: string,
    sex: number,
    birthday: string,
    state: number,
    createTime: string,
}

interface Category {
    id: string, 
    name: string,
    pathName: string,
    fatherId: string,
    createTime: string,
}

interface Article {
    id: string,
    title: string,
    user: User,
    watch: number,
    firstCategory: Category,
    secondCategory: Category,
    level: string,
    modifyTime: string,
}

export type { User, Category, Article };