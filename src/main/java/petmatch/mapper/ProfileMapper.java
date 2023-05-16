package petmatch.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import petmatch.model.Breed;
import petmatch.model.Gallery;
import petmatch.model.Interests;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileMapper {
    private final ModelMapper mapper;

    public static List<Breed> buildBreedsFromInterests(List<Interests> interestsList) {
        return interestsList.stream().map(Interests::getBreed).toList();
    }

    public static List<String> buildGalleryResponse(List<Gallery> galleries) {
        return galleries.stream().map(Gallery::getGallery).toList();
    }
}
