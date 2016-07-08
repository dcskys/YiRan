package com.dc.androidtool.search.entity;

import java.util.List;

/**
 * 书籍详情页实体
 */
public class BookDetailEntity {

    /**
     * max : 10
     * numRaters : 4
     * average : 0.0
     * min : 0
     */

    private RatingBean rating;
    /**
     * rating : {"max":10,"numRaters":4,"average":"0.0","min":0}
     * subtitle :
     * author : ["罗伯特"]
     * pubdate : 1900-01-01
     * tags : [{"count":7,"name":"平衡计分卡","title":"平衡计分卡"},{"count":7,"name":"管理","title":"管理"},{"count":6,"name":"战略","title":"战略"},{"count":3,"name":"卡普兰","title":"卡普兰"},{"count":2,"name":"人力资源","title":"人力资源"},{"count":2,"name":"经典","title":"经典"},{"count":2,"name":"商业","title":"商业"},{"count":2,"name":"管理学名著","title":"管理学名著"}]
     * origin_title :
     * image : https://img3.doubanio.com/mpic/s2358425.jpg
     * binding : 平装
     * translator : ["罗伯特·S·卡普兰"]
     * catalog :

     * pages :
     * images : {"small":"https://img3.doubanio.com/spic/s2358425.jpg","large":"https://img3.doubanio.com/lpic/s2358425.jpg","medium":"https://img3.doubanio.com/mpic/s2358425.jpg"}
     * alt : https://book.douban.com/subject/1525392/
     * id : 1525392
     * publisher : 人民邮电出版社
     * isbn10 : 7115118957
     * isbn13 : 9787115118950
     * title : 战略中心型组织
     * url : https://api.douban.com/v2/book/1525392
     * alt_title :
     * author_intro : 罗伯特·S·卡普兰（Robert S. Kaplan）是哈佛商学院研究领导能力开发的马文·鲍尔（Marvin Bower）讲座教授。他是哈佛商学院出版社出版的《平衡计分卡和相关损失》(The Balanced Scorecard and Relevance Lost) 一书的合著者，并是哈佛商学院出版社出版的《制造精良的测评》（Measures for Manufacturing Excellence）另一本书的编辑。
     * summary : 来自于平衡记分卡创立者的一种新的、有效的绩效管理方法。在今天的商业环境中，战略从来没有显得这样重要过。但研究表明，大多数企业仍不能成功地实施战略。在浩繁的记录背后隐藏着一个无法否认的事实：大多数企业仍然继续使用专门为传统组织而设计的——组织管理严密的、财务驱动的和战术性的——管理流程。
     平衡记分卡被称为革命性的绩效管理工具。现在，其创立者又介绍了一种新方法，它使战略成为连续的流程，不仅被高层管理者拥有，而且被组织中每一个人所掌握。在本书中，卡普兰和诺顿分享了实施平衡记分卡的200多家企业长达10年的经验，并从中抽取了20多个案例——包括美孚石油公司、CIGNA财产人寿保险公司、AT&T加拿大公司——进行专门研究。卡普兰和诺顿解释了平衡记分卡的使用者如何将该工具的应用提高到另一个水准。这些组织已经运用平衡记分卡开创了一个全新的绩效管理框架——将战略置于关键管理流程和系统的中心位置。
     卡普兰和诺顿阐明了构筑以战略为中心的组织的五项关键原则：
     1．将战略转变为业务术语，
     2．使组织与战略一致；
     3. 使战略成为每个人的日常工作；
     4．使战略成为连续的过程；
     5．通过果断、有效的领导方式动员变革。
     * price : 50.0
     */

    private String subtitle;
    private String pubdate;
    private String origin_title;
    private String image;
    private String binding;
    private String catalog;
    private String pages;
    /**
     * small : https://img3.doubanio.com/spic/s2358425.jpg
     * large : https://img3.doubanio.com/lpic/s2358425.jpg
     * medium : https://img3.doubanio.com/mpic/s2358425.jpg
     */

    private ImagesBean images;
    private String alt;
    private String id;
    private String publisher;
    private String isbn10;
    private String isbn13;
    private String title;
    private String url;
    private String alt_title;
    private String author_intro;
    private String summary;
    private String price;
    private List<String> author;
    /**
     * count : 7
     * name : 平衡计分卡
     * title : 平衡计分卡
     */

    private List<TagsBean> tags;
    private List<String> translator;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getOrigin_title() {
        return origin_title;
    }

    public void setOrigin_title(String origin_title) {
        this.origin_title = origin_title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlt_title() {
        return alt_title;
    }

    public void setAlt_title(String alt_title) {
        this.alt_title = alt_title;
    }

    public String getAuthor_intro() {
        return author_intro;
    }

    public void setAuthor_intro(String author_intro) {
        this.author_intro = author_intro;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<String> getTranslator() {
        return translator;
    }

    public void setTranslator(List<String> translator) {
        this.translator = translator;
    }

    public static class RatingBean {
        private int max;
        private int numRaters;
        private String average;
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public int getNumRaters() {
            return numRaters;
        }

        public void setNumRaters(int numRaters) {
            this.numRaters = numRaters;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class ImagesBean {
        private String small;
        private String large;
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class TagsBean {
        private int count;
        private String name;
        private String title;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
