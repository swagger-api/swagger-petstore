/**
 *  Copyright 2016 SmartBear Software
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.swagger.sample.data;

import io.swagger.sample.model.Category;
import io.swagger.sample.model.Pet;
import io.swagger.sample.model.Tag;

import java.util.*;

public class PetData {

  public static final Long MAX_SIZE = 1000L;

  static Map<Long, Pet> pets = Collections.synchronizedMap(new LinkedHashMap<Long, Pet>());
  static Map<Long, Category> categories = Collections.synchronizedMap(new LinkedHashMap<Long, Category>());

  static {
    categories.put(1L, createCategory(1, "Dogs"));
    categories.put(2L, createCategory(2, "Cats"));
    categories.put(3L, createCategory(3, "Rabbits"));
    categories.put(4L, createCategory(4, "Lions"));

    pets.put(1L, createPet(1, categories.get(2L), "Cat 1", new String[] {
        "url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
    pets.put(2L, createPet(2, categories.get(2L), "Cat 2", new String[] {
        "url1", "url2" }, new String[] { "tag2", "tag3" }, "available"));
    pets.put(3L, createPet(3, categories.get(2L), "Cat 3", new String[] {
        "url1", "url2" }, new String[] { "tag3", "tag4" }, "pending"));

    pets.put(4L, createPet(4, categories.get(1L), "Dog 1", new String[] {
        "url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
    pets.put(5L, createPet(5, categories.get(1L), "Dog 2", new String[] {
        "url1", "url2" }, new String[] { "tag2", "tag3" }, "sold"));
    pets.put(6L, createPet(6, categories.get(1L), "Dog 3", new String[] {
        "url1", "url2" }, new String[] { "tag3", "tag4" }, "pending"));

    pets.put(7L, createPet(7, categories.get(4L), "Lion 1", new String[] {
        "url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
    pets.put(8L, createPet(8, categories.get(4L), "Lion 2", new String[] {
        "url1", "url2" }, new String[] { "tag2", "tag3" }, "available"));
    pets.put(9L, createPet(9, categories.get(4L), "Lion 3", new String[] {
        "url1", "url2" }, new String[] { "tag3", "tag4" }, "available"));

    pets.put(10L, createPet(10, categories.get(3L), "Rabbit 1", new String[] {
        "url1", "url2" }, new String[] { "tag3", "tag4" }, "available"));
  }

  public Pet getPetById(long petId) {
    return pets.get(Long.valueOf(petId));
  }

  public boolean deletePet(long petId) {
    return pets.remove(Long.valueOf(petId)) == null ? false : true;
  }

  public List<Pet> findPetByStatus(String status) {
    List<Pet> result = new java.util.ArrayList<Pet>();
    if(status == null) {
      return result;
    }
    String[] statuses = status.split(",");
    for (Pet pet : pets.values()) {
      for (String s : statuses) {
        if (s.equals(pet.getStatus())) {
          result.add(pet);
        }
      }
    }
    return result;
  }

  public List<Pet> findPetByTags(String tags) {
    List<Pet> result = new java.util.ArrayList<Pet>();

    if(tags == null) {
      return result;
    }
    String[] tagList = tags.split(",");
    for (Pet pet : pets.values()) {
      if (null != pet.getTags()) {
        for (Tag tag : pet.getTags()) {
          for (String tagListString : tagList) {
            if (tagListString.equals(tag.getName()))
              result.add(pet);
          }
        }
      }
    }
    return result;
  }

  public Pet addPet(Pet pet) {
    if(pet.getId() <1) {
      long maxId = 0;
      for (Long petId: pets.keySet()) {
        if(petId > maxId) {
          maxId = petId;
        }
      }
      long newId = maxId > Long.MAX_VALUE -1 ? maxId : maxId + 1;
      pet.setId(newId);
    }
    pets.put(pet.getId(), pet);
    if (pets.size() > MAX_SIZE) {
      Long id = pets.keySet().iterator().next();
      pets.remove(id);
    }

    return pet;
  }

  public Map<String, Integer> getInventoryByStatus() {
    Map<String, Integer> output = new HashMap<String, Integer>();
    for(Pet pet : pets.values()) {
      String status = pet.getStatus();
      if(status != null && !"".equals(status)) {
        Integer count = output.get(status);
        if(count == null)
          count = new Integer(1);
        else
          count = count.intValue() + 1;
        output.put(status, count);
      }
    }
    return output;
  }

  static Pet createPet(long id, Category cat, String name, String[] urls,
      String[] tags, String status) {
    Pet pet = new Pet();
    pet.setId(id);
    pet.setCategory(cat);
    pet.setName(name);
    if (null != urls) {
      List<String> urlObjs = new ArrayList<String>();
      for (String urlString : urls) {
        urlObjs.add(urlString);
      }
      pet.setPhotoUrls(urlObjs);
    }
    List<Tag> tagObjs = new java.util.ArrayList<Tag>();
    int i = 0;
    if (null != tags) {
      for (String tagString : tags) {
        i = i + 1;
        Tag tag = new Tag();
        tag.setId(i);
        tag.setName(tagString);
        tagObjs.add(tag);
      }
    }
    pet.setTags(tagObjs);
    pet.setStatus(status);
    return pet;
  }

  static Category createCategory(long id, String name) {
    Category category = new Category();
    category.setId(id);
    category.setName(name);
    return category;
  }
}
