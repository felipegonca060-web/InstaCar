package br.goiasrp.launcher;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private final List<Car> carros = Arrays.asList(
        new Car("Volkswagen", "Gol G7", "2016", "1.6 Flex", "R$ 42.000"),
        new Car("Chevrolet", "Onix", "2020", "1.0 Turbo", "R$ 68.000"),
        new Car("Fiat", "Uno Way", "2018", "1.3 Flex", "R$ 45.000"),
        new Car("Toyota", "Corolla", "2021", "2.0 Flex", "R$ 118.000"),
        new Car("Honda", "Civic", "2020", "2.0 Flex", "R$ 110.000"),
        new Car("Hyundai", "HB20", "2022", "1.0 Flex", "R$ 72.000"),
        new Car("Ford", "Ka", "2019", "1.5 Flex", "R$ 52.000"),
        new Car("Renault", "Kwid", "2021", "1.0 Flex", "R$ 55.000")
    );

    private LinearLayout lista;
    private EditText busca;
    private String categoria = "Todos";

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        criarTela();
    }

    private void criarTela() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.rgb(5, 10, 18));

        TextView title = texto("🚘  BRASIL CARROS", 25, Color.WHITE, true);
        title.setPadding(20, 22, 20, 2);
        root.addView(title, new LinearLayout.LayoutParams(-1, 58));

        TextView sub = texto("Catálogo de carros brasileiros", 14, Color.LTGRAY, false);
        sub.setPadding(20, 0, 20, 12);
        root.addView(sub);

        busca = new EditText(this);
        busca.setHint("🔎  Buscar carro ou marca...");
        busca.setHintTextColor(Color.GRAY);
        busca.setTextColor(Color.WHITE);
        busca.setSingleLine(true);
        busca.setPadding(18, 0, 18, 0);
        busca.setBackground(caixa(Color.rgb(17, 29, 43), Color.rgb(25, 120, 220)));
        LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(-1, 52);
        bp.setMargins(16, 0, 16, 12);
        root.addView(busca, bp);

        HorizontalScrollView hsv = new HorizontalScrollView(this);
        hsv.setHorizontalScrollBarEnabled(false);
        LinearLayout filters = new LinearLayout(this);
        filters.setPadding(12, 0, 12, 10);

        String[] marcas = {"Todos","Volkswagen","Chevrolet","Fiat","Toyota","Honda","Hyundai","Ford","Renault"};
        for (String marca : marcas) {
            Button b = new Button(this);
            b.setText(marca);
            b.setTextColor(Color.WHITE);
            b.setTextSize(12);
            b.setAllCaps(false);
            b.setBackground(caixa(Color.rgb(17, 29, 43), Color.rgb(30, 85, 140)));
            LinearLayout.LayoutParams fp = new LinearLayout.LayoutParams(-2, 46);
            fp.setMargins(3, 0, 3, 0);
            filters.addView(b, fp);
            b.setOnClickListener(v -> {
                categoria = b.getText().toString();
                atualizar();
            });
        }
        hsv.addView(filters);
        root.addView(hsv);

        ScrollView scroll = new ScrollView(this);
        lista = new LinearLayout(this);
        lista.setOrientation(LinearLayout.VERTICAL);
        lista.setPadding(16, 0, 16, 20);
        scroll.addView(lista);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        busca.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int st, int before, int count) { atualizar(); }
            public void afterTextChanged(Editable e) {}
        });

        setContentView(root);
        atualizar();
    }

    private void atualizar() {
        lista.removeAllViews();
        String termo = busca.getText().toString().toLowerCase();

        for (Car c : carros) {
            boolean marcaOk = categoria.equals("Todos") || c.marca.equals(categoria);
            boolean buscaOk = c.marca.toLowerCase().contains(termo)
                    || c.modelo.toLowerCase().contains(termo);
            if (marcaOk && buscaOk) card(c);
        }

        if (lista.getChildCount() == 0) {
            TextView vazio = texto("Nenhum carro encontrado.", 16, Color.LTGRAY, false);
            vazio.setGravity(Gravity.CENTER);
            lista.addView(vazio, new LinearLayout.LayoutParams(-1, 130));
        }
    }

    private void card(Car c) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(16, 14, 16, 14);
        box.setBackground(caixa(Color.rgb(13, 25, 39), Color.rgb(25, 90, 150)));

        TextView carIcon = texto("🚘", 46, Color.WHITE, false);
        carIcon.setGravity(Gravity.CENTER);
        box.addView(carIcon, new LinearLayout.LayoutParams(-1, 72));

        box.addView(texto(c.marca + " " + c.modelo, 19, Color.WHITE, true));
        box.addView(texto("Ano " + c.ano + "  •  " + c.motor, 14, Color.LTGRAY, false));

        TextView price = texto(c.preco, 18, Color.rgb(75, 190, 255), true);
        price.setPadding(0, 7, 0, 7);
        box.addView(price);

        Button detail = new Button(this);
        detail.setText("VER DETALHES");
        detail.setAllCaps(false);
        detail.setTextColor(Color.WHITE);
        detail.setBackground(caixa(Color.rgb(20, 110, 210), Color.rgb(70, 170, 255)));
        box.addView(detail, new LinearLayout.LayoutParams(-1, 48));
        detail.setOnClickListener(v -> detalhes(c));

        LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
        cp.setMargins(0, 0, 0, 14);
        lista.addView(box, cp);
    }

    private void detalhes(Car c) {
        new AlertDialog.Builder(this)
            .setTitle("🚘 " + c.marca + " " + c.modelo)
            .setMessage(
                "Marca: " + c.marca +
                "\nModelo: " + c.modelo +
                "\nAno: " + c.ano +
                "\nMotor: " + c.motor +
                "\nPreço de referência: " + c.preco +
                "\n\nPrimeira versão do catálogo. Fotos, favoritos e mais especificações podem ser adicionados nas próximas versões."
            )
            .setPositiveButton("Fechar", null)
            .show();
    }

    private TextView texto(String s, float size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(s);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private GradientDrawable caixa(int fill, int stroke) {
        GradientDrawable g = new GradientDrawable();
        g.setColor(fill);
        g.setStroke(2, stroke);
        g.setCornerRadius(18);
        return g;
    }

    private static class Car {
        String marca, modelo, ano, motor, preco;
        Car(String marca, String modelo, String ano, String motor, String preco) {
            this.marca = marca;
            this.modelo = modelo;
            this.ano = ano;
            this.motor = motor;
            this.preco = preco;
        }
    }
}
