package com.dc.androidtool.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dc.androidtool.R;
import com.dc.androidtool.search.entity.BookItemData;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 书籍的适配器
 */
public class BookRecyclerAdapter  extends RecyclerView.Adapter<BookRecyclerViewHolder>{
    private Context context;
    private List<BookItemData.BooksBean>  list;
    private ImageLoader imageLoader;

    public BookRecyclerAdapter(Context context, List<BookItemData.BooksBean> list) {
        this.context = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
    }

    @Override
    public BookRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //优化每一个item 布局
        BookRecyclerViewHolder holder = new BookRecyclerViewHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.fragment_book_item, parent,
                false));
        return  holder;
    }

    @Override
    public void onBindViewHolder(BookRecyclerViewHolder holder, int position) {

        final BookItemData.BooksBean booksBean = list.get(position); //BookItemData类的某一项
        final BookItemData.BooksBean.RatingBean ratingBean =booksBean.getRating();
        final BookItemData.BooksBean.ImagesBean imagesBean =booksBean.getImages();
        final List<String> author = booksBean.getAuthor(); //作者列表
        final List<String> translator = booksBean.getTranslator(); //翻译者

      imageLoader.displayImage(imagesBean.getMedium(), holder.bookImage);  //加载中等图片，另一种加载方式

        //set others
        holder.bookName.setText(booksBean.getTitle()); //书名
        holder.bookPoints.setText(ratingBean.getAverage());//评分
        holder.bookPublisher.setText( booksBean.getPublisher()); //出版
        holder.bookPubdate.setText(booksBean.getPubdate()); //日期
        holder.bookPrice.setText(booksBean.getPrice()); //价格

        //作者显示处理
        StringBuilder authorString = new StringBuilder();
        for (int i = 0; i < author.size(); i++) {
            if (i < author.size() - 1) {
                authorString.append(author.get(i));
                authorString.append("、");
            } else {
                authorString.append(author.get(i));
            }
        }
        holder.bookAuthor.setText(authorString.toString());


        //set translator翻译者 处理
        if (translator.size()==0) {
            holder.bookDivider.setVisibility(View.GONE);
            holder.bookTranslator.setText("");
        } else {
            holder.bookTranslator.setText(translator.get(0)+ " 译");
        }

        //set card view
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetailActivity.class);
                intent.putExtra("bookId",booksBean.getId() );
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class BookRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView bookImage;
    TextView bookName;
    TextView bookPoints;
    TextView bookAuthor;
    TextView bookTranslator;
    TextView bookPublisher;
    TextView bookPubdate;
    TextView bookPrice;
    TextView bookDivider;
    CardView cardView;

    public BookRecyclerViewHolder(View itemView) {
        super(itemView);
        bookImage = (ImageView) itemView.findViewById(R.id.fragment_book_item_image);
        bookName = (TextView) itemView.findViewById(R.id.fragment_book_item_title);
        bookPoints = (TextView) itemView.findViewById(R.id.fragment_book_item_points);
        bookAuthor = (TextView) itemView.findViewById(R.id.fragment_book_item_author);
        bookTranslator = (TextView) itemView.findViewById(R.id.fragment_book_item_translator);
        bookPublisher = (TextView) itemView.findViewById(R.id.fragment_book_item_publisher);
        bookPubdate = (TextView) itemView.findViewById(R.id.fragment_book_item_pubdate);
        bookPrice = (TextView) itemView.findViewById(R.id.fragment_book_item_price);
        bookDivider = (TextView) itemView.findViewById(R.id.fragment_book_item_divider);
        cardView = (CardView) itemView.findViewById(R.id.fragment_book_item);
    }
}
