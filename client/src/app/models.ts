export interface Bundle{

    // private String bundleId;
    // private Date date;
    // private String title;
    // private String name;
    // private String comments;
    // private List<String> urls = new ArrayList<>();

    bundleId:string,
    date:Date,
    title:string,
    name:string,
    comments:string,
    urls:string[]

}

export interface GetResponse{

    bundleId:string,
    date:string,
    title:string,

}