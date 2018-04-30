package com.skills.interapt.retrofitpokemonworkshop;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.skills.interapt.retrofitpokemonworkshop.MainActivity.POKEMON_NAME;

public class PokemonInfoFragment extends Fragment {

    @BindView(R.id.pokemon_name_textview)
    protected TextView displayName;
    @BindView(R.id.details_textview)
    protected TextView pokemonDetails;
    @BindView(R.id.pokemon_imageview)
    protected ImageView pokemonImage;


    private String baseUrl = "http://pokeapi.co/api/v2/";
    private Retrofit retrofit;
    private PokemonAPICalls pokemonAPICalls;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pokemon_info, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    public static PokemonInfoFragment newInstance() {

        Bundle args = new Bundle();

        PokemonInfoFragment fragment = new PokemonInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();

        String pokemonName = getArguments().getString(POKEMON_NAME);

        buildRetrofit();

        makeApiCalls(pokemonName);
    }

    private void makeApiCalls(final String pokemonName) {
        pokemonAPICalls.getPokemonInfo(pokemonName).enqueue(new Callback<PokemonAPICalls.PokemonInfo>() {
            @Override
            public void onResponse(Call<PokemonAPICalls.PokemonInfo> call, Response<PokemonAPICalls.PokemonInfo> response) {
                if(response.isSuccessful()) {
                    displayName.setText((response.body().getName()));
                    Glide.with(getContext()).load(response.body().getSprites().getDefaultImage()).into(pokemonImage);
                    makeSecondApiCall(response.body().getId());


                } else {
                    Toast.makeText(getContext(), "First call not successful but didn't fail", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PokemonAPICalls.PokemonInfo> call, Throwable t) {

            }
        });

    }

    private void makeSecondApiCall(int pokemonId) {

        pokemonAPICalls.getPokemonAbilities(pokemonId).enqueue(new Callback<PokemonAPICalls.PokemonAbilities>() {
            @Override
            public void onResponse(Call<PokemonAPICalls.PokemonAbilities> call, Response<PokemonAPICalls.PokemonAbilities> response) {
                if(response.isSuccessful()) {
                    pokemonDetails.setText(response.body().getPokemonEffectsList().get(0).getEffect());
                } else {
                    Toast.makeText(getContext(), "Second call not successful but didn't fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonAPICalls.PokemonAbilities> call, Throwable t) {

            }
        });

    }

    private void buildRetrofit() {

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        pokemonAPICalls = retrofit.create(PokemonAPICalls.class);
    }
}
