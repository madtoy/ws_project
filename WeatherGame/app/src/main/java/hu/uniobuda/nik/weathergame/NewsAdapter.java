package hu.uniobuda.nik.weathergame;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    List<News> items;

    public NewsAdapter(List<News> items) {
        this.items = items;
    }

    @Override
    public boolean areAllItemsEnabled() { //nem kattinthatóak az elemek
        return false;
    }

    @Override
    public boolean isEnabled(int position) { //minden páros elem kattintható
        return position % 2 == 0;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        News news = items.get(position);
        ViewHolder holder;

        if (convertView == null) {

            convertView = View.inflate(parent.getContext(), R.layout.listitem_news, null);

            holder = new ViewHolder();
            holder.title = (AppCompatTextView) convertView.findViewById(R.id.title);
            holder.lead = (AppCompatTextView) convertView.findViewById(R.id.lead);
            convertView.setTag(holder);
        }
        else
        {

            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(news.getTitle());
        holder.lead.setText(
                news.getContent().substring(
                        0,
                        Math.min(
                                130, news.getContent().length()
                        )
                )
        );

        return convertView;
    }

    class ViewHolder {
        AppCompatTextView title, lead;

    }
}
