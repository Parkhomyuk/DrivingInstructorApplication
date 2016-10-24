package com.inetex.drivinginstructorapplication;

import android.content.Context;

    import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import java.io.InputStream;

import java.io.IOException;

import java.util.ArrayList;


public class BoxAdapter extends BaseAdapter  {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Instructors> objects;
    ImageView image;
    Instructors instructors;
    BoxAdapter(Context context, ArrayList<Instructors> instructors) {
        ctx = context;
        objects = instructors;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item_instructors, parent, false);
        }

         instructors = getInstructor(position);

        // заполняем View в пункте списка данными

        ((TextView) view.findViewById(R.id.tvName)).setText(instructors.name);
        ((TextView) view.findViewById(R.id.tvCity)).setText(instructors.city);
        ((TextView) view.findViewById(R.id.tvExperience)).setText(instructors.experience+"");
        ((TextView) view.findViewById(R.id.tvRating)).setText(instructors.rating+"");
        ((TextView) view.findViewById(R.id.tvTypeVeh)).setText(instructors.typeVehicle);

        /*((ImageView) view.findViewById(R.id.ivImage)).setImageResource(instructors.avatar);*/
        image=(ImageView) view.findViewById(R.id.ivImage);
        loadImageFromAsset(instructors.avatar);

       /* File imagePath = new File("/assets/angela.jpg"  );

        Picasso.with(ctx)
                *//*.load("http://static.parset.com/Files/Galleries/8/8aHDJQok.jpg")*//*
                .load(imagePath)
                .placeholder(R.drawable.ic_account_circle_black_24dp)
                .error(R.drawable.ic_cancel_black_24dp)
                .into(image);*/

       /* CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из инструкторов: в корзине или нет
        cbBuy.setChecked(instructors.box);*/
        return view;
    }

    // Instructor по позиции
    Instructors getInstructor(int position) {
        return ((Instructors) getItem(position));
    }

   /* // contents box
    ArrayList<Instructors> getBox() {
        ArrayList<Instructors> box = new ArrayList<Instructors>();
        for (Instructors instr : objects) {
            // if in box
            if (instr.box)
                box.add(instr);
        }
        return box;
    }*/


    //obrabotka tach instructor


   /* // обработчик для чекбоксов
    CompoundButton.OnCheckedChangeListener myCheckChangeList = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {

            getInstructor((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/

     public void loadImageFromAsset(String str) {
         try {
             // получаем входной поток
             InputStream ims = ctx.getAssets().open(str);
             // загружаем как Drawable
             Drawable d = Drawable.createFromStream(ims, null);
             // выводим картинку в ImageView
             image.setImageDrawable(d);
         } catch (IOException ex) {
             return;
         }
     }
    }




