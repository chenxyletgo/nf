
interface Comment {
    id: string,
    userId: string,
    userName: string,
    userAvatar: string,
    words: string,
    time: string,
    like: number,
    dislike: number,
    active: number, //-1未评价 0喜欢 1不喜欢
}

export type { Comment };