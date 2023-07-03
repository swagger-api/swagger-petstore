package io.swagger.petstore.data;

import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.PetDetails;
import io.swagger.petstore.model.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class PetData {
    private static Map<Long, Pet> pets = new LinkedHashMap<>();
    private static List<Category> categories = new ArrayList<>();

    static {
        categories.add(createCategory(1, "Dogs"));
        categories.add(createCategory(2, "Cats"));
        categories.add(createCategory(3, "Rabbits"));
        categories.add(createCategory(4, "Lions"));

        pets.put(1L, createPet(1, categories.get(1), "Cat 1", new String[]{
                "url1", "url2"}, new String[]{"tag1", "tag2"}, "available", createPetDetails(1, categories.get(1), 1, "tag1")));
        pets.put(2L, createPet(2, categories.get(1), "Cat 2", new String[]{
                "url1", "url2"}, new String[]{"tag2", "tag3"}, "available", createPetDetails(1, categories.get(1), 2, "tag2")));
        pets.put(3L, createPet(3, categories.get(1), "Cat 3", new String[]{
                "url1", "url2"}, new String[]{"tag3", "tag4"}, "pending", createPetDetails(1, categories.get(1), 3, "tag3")));

        pets.put(4L, createPet(4, categories.get(0), "Dog 1", new String[]{
                "url1", "url2"}, new String[]{"tag1", "tag2"}, "available", createPetDetails(1, categories.get(0), 1, "tag1")));

        pets.put(5L, createPet(5, categories.get(0), "Dog 2", new String[]{
                "url1", "url2"}, new String[]{"tag2", "tag3"}, "sold", createPetDetails(1, categories.get(0), 2, "tag2")));

        pets.put(6L, createPet(6, categories.get(0), "Dog 3", new String[]{
                "url1", "url2"}, new String[]{"tag3", "tag4"}, "pending", createPetDetails(1, categories.get(0), 3, "tag3")));

        pets.put(7L, createPet(7, categories.get(3), "Lion 1", new String[]{
                "url1", "url2"}, new String[]{"tag1", "tag2"}, "available", createPetDetails(1, categories.get(3), 1, "tag1")));
        pets.put(8L, createPet(8, categories.get(3), "Lion 2", new String[]{
                "url1", "url2"}, new String[]{"tag2", "tag3"}, "available", createPetDetails(1, categories.get(3), 3, "tag3")));
        pets.put(9L, createPet(9, categories.get(3), "Lion 3", new String[]{
                "url1", "url2"}, new String[]{"tag3", "tag4"}, "available", createPetDetails(1, categories.get(3), 3, "tag3")));

        pets.put(10L, createPet(10, categories.get(2), "Rabbit 1", new String[]{
                "url1", "url2"}, new String[]{"tag3", "tag4"}, "available", createPetDetails(1, categories.get(2), 3, "tag3")));
    }

    public Pet getPetById(final long petId) {
        return pets.get(petId);
    }

    public List<Pet> findPetByStatus(final String status) {
        final String[] statues = status.split(",");
        final List<Pet> result = new ArrayList<>();
        for (final Pet pet : pets.values()) {
            for (final String s : statues) {
                if (s.equals(pet.getStatus())) {
                    result.add(pet);
                }
            }
        }
        return result;
    }

    public List<Pet> findPetByTags(final List<String> tags) {
        final List<Pet> result = new ArrayList<>();
        for (final Pet pet : pets.values()) {
            if (null != pet.getTags()) {
                for (final Tag tag : pet.getTags()) {
                    for (final String tagListString : tags) {
                        if (tagListString.equals(tag.getName())) {
                            result.add(pet);
                        }
                    }
                }
            }
        }
        return result;
    }

    public void traverse(List<Pet> pets, Object param) {
    }
    public void addPet(final Pet pet) {
        if (pets.size() > 1000 && pet.getId() != null && pets.get(pet.getId()) != null) {
            pets.remove(pets.keySet().toArray(new Pet[0])[0]);
        }
        if (pet.getId() == null || pet.getId() < 0) {
            pet.setId(Math.abs(new Random().nextLong()));
        }
        pets.put(pet.getId(), pet);
    }

    public void deletePetById(final Long petId) {
        pets.remove(petId);
    }

    public static Pet createPet(final Long id, final Category cat, final String name,
                                final List<String> urls, final List<Tag> tags, final String status, final PetDetails petDetails) {
        final Pet pet = new Pet();
        pet.setId(id);
        pet.setCategory(cat);
        pet.setName(name);
        pet.setPhotoUrls(urls);
        pet.setTags(tags);
        pet.setStatus(status);
        pet.setPetDetails(petDetails);
        return pet;
    }

    private static Pet createPet(final long id, final Category cat, final String name, final String[] urls,
                                 final String[] tags, final String status, final PetDetails petDetails) {
        final Pet pet = new Pet();
        pet.setId(id);
        pet.setCategory(cat);
        pet.setName(name);
        if (null != urls) {
            final List<String> urlObjs = new ArrayList<>(Arrays.asList(urls));
            pet.setPhotoUrls(urlObjs);
        }
        final List<Tag> tagObjs = new ArrayList<>();
        int i = 0;
        if (null != tags) {
            for (final String tagString : tags) {
                i = i + 1;
                final Tag tag = new Tag();
                tag.setId(i);
                tag.setName(tagString);
                tagObjs.add(tag);
            }
        }
        pet.setTags(tagObjs);
        pet.setStatus(status);
        pet.setPetDetails(petDetails);
        return pet;
    }

    private static Category createCategory(final long id, final String name) {
        final Category category = new Category();
        category.setId(id);
        category.setName(name);
        return category;
    }
    private static PetDetails createPetDetails(final long id, final Category cat, long tagId, String tagName) {
        final PetDetails petDetails = new PetDetails();
        petDetails.setId(id);
        petDetails.setCategory(cat);
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(tagName);
        petDetails.setTag(tag);
        return petDetails;
    }

}
