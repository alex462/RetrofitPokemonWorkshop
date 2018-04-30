package com.skills.interapt.retrofitpokemonworkshop;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.user_pokemon_edittext)
    protected TextInputEditText pokemonName;

    private PokemonInfoFragment pokemonInfoFragment;

    public static final String POKEMON_NAME = "pokemon_name";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_button)
    protected void submitClicked() {

        if(pokemonName.getText().toString().isEmpty()) {
            Toast.makeText(this, "You must enter a name!", Toast.LENGTH_SHORT).show();
        } else {

            pokemonInfoFragment = PokemonInfoFragment.newInstance();

            Bundle bundle = new Bundle();
            bundle.putString(POKEMON_NAME, pokemonName.getText().toString().toLowerCase());
            pokemonInfoFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, pokemonInfoFragment).commit();

        }

    }

    @Override
    public void onBackPressed() {
        if(pokemonInfoFragment.isVisible()) {
            getSupportFragmentManager().beginTransaction().remove(pokemonInfoFragment).commit();
        } else {
            super.onBackPressed();
        }
    }
}
