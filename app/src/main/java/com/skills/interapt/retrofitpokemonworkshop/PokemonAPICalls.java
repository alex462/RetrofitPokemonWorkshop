package com.skills.interapt.retrofitpokemonworkshop;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokemonAPICalls {

    @GET("pokemon/{name}")
    Call<PokemonInfo> getPokemonInfo(@Path("name") String pokemonName);

    @GET("ability/{name}")
    Call<PokemonAbilities> getPokemonAbilities(@Path("name") int pokemonId);




    class PokemonInfo {

        @SerializedName("name")
        private String name;
        @SerializedName("sprites")
        private Sprites sprites;
        @SerializedName("id")
        private int id;

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }

        public Sprites getSprites() {
            return sprites;
        }

        class Sprites {

            @SerializedName("front_default")
            private String defaultImage;

            public String getDefaultImage() {
                return defaultImage;
            }
        }
    }

    class PokemonAbilities {
        @SerializedName("effect_entries")
        private List<PokemonEffects> pokemonEffectsList;

        public List<PokemonEffects> getPokemonEffectsList() {
            return pokemonEffectsList;
        }

        class PokemonEffects {

            @SerializedName("effect")
            private String effect;

            public String getEffect() {
                return effect;
            }
        }
    }


}
