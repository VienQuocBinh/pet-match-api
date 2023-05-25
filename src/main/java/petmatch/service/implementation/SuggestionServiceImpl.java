package petmatch.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import petmatch.model.Interests;
import petmatch.model.Match;
import petmatch.model.Profile;
import petmatch.service.InterestService;
import petmatch.service.MatchService;
import petmatch.service.ProfileService;
import petmatch.service.SuggestionService;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService {
    private final ProfileService profileService;
    private final InterestService interestService;
    private final MatchService matchService;

    @Override
    public List<Profile> suggestProfiles(Profile myProfile) {
        // Get the list of matched profiles
        List<Match> previousMatches = matchService.getPreviousMatches(myProfile);
        // Retrieve profile IDs from previous matches
        Set<String> matchedProfileIds = previousMatches.stream()
                .map(match -> match.getMatchTo().getId().toString())
                .collect(Collectors.toSet());
        // Get interests of profile
        List<Interests> interests = interestService.getInterestsByProfile(myProfile);

        // Get list of profiles base on interest excepted matched profiles
        List<Profile> suggestedProfiles = new ArrayList<>();
        for (Interests interest : interests) {
            List<Profile> profilesWithInterest = profileService.getProfilesByInterest(interest);

            // Filter out profiles that have been previously matched
            List<Profile> filteredProfiles = profilesWithInterest.stream()
                    .filter(profile -> !matchedProfileIds.contains(profile.getId().toString()))
                    .toList();

            suggestedProfiles.addAll(filteredProfiles);
        }

        // Sort suggested profiles by a scoring metric (e.g., similarity score, relevance)
        suggestedProfiles.sort(Comparator.comparingDouble(profile -> calculateSimilarityScore(myProfile, profile)));
        return suggestedProfiles;
    }

    private double calculateSimilarityScore(Profile myProfile, Profile profile) {
//        // Define the factors to consider for similarity scoring
        double interestsFactor = 0.6;  // Weight for shared interests
        double profileAttributesFactor = 0.4;  // Weight for profile attributes

        // Calculate the similarity score based on the factors
        double interestsScore = calculateInterestsScore(myProfile, profile) * interestsFactor;
        double profileAttributesScore = calculateProfileAttributesScore(myProfile, profile) * profileAttributesFactor;
//
//        // Combine the scores to get the overall similarity score
//
        return interestsScore + profileAttributesScore;
    }

    private int calculateAgeDifference(Date myBirthday, Date otherBirthday) {
        LocalDate myLocalDate = myBirthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate otherLocalDate = otherBirthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(myLocalDate, otherLocalDate);
        return period.getYears();
    }

    private double calculateInterestsScore(Profile myProfile, Profile profile) {
        // Retrieve the interests of the user and the profile
        List<Interests> userInterests = interestService.getInterestsByProfile(myProfile);
        List<Interests> profileInterests = interestService.getInterestsByProfile(profile);

        // Calculate the number of shared interests
        long sharedInterestsCount = userInterests.stream()
                .filter(profileInterests::contains)
                .count();

        // Calculate the score based on the number of shared interests
        return sharedInterestsCount / (double) userInterests.size();
    }

    private double calculateProfileAttributesScore(Profile myProfile, Profile profile) {
// Define relative weights for each criterion
        double genderWeight = 1.0;
        double ageWeight = 0.8;
        double weightWeight = 0.5;
        double heightWeight = 0.5;

        // Define satisfaction rate (between 0 and 1)
        double satisfactionRate = 0.8;
        // Initialize score
        double score = 0.0;

        // Increment score based on criteria match and relative weights
        if (myProfile.getGender() == profile.getGender()) {
            score += genderWeight;
        }

        int ageDifference = calculateAgeDifference(myProfile.getBirthday(), profile.getBirthday());
        int minAgeDifference = 18;
        int maxAgeDifference = 40;
        if (ageDifference >= minAgeDifference && ageDifference <= maxAgeDifference) {
            score += ageWeight;
        }


        Double minWeight = myProfile.getWeight() - 10;
        double maxWeight = myProfile.getWeight() + 10;
        if (profile.getWeight() >= minWeight && profile.getWeight() <= maxWeight) {
            score += weightWeight;
        }

        Double minHeight = myProfile.getHeight() - 10;
        double maxHeight = myProfile.getHeight() + 10;
        if (profile.getHeight() >= minHeight && profile.getHeight() <= maxHeight) {
            score += heightWeight;
        }
        // Example: Compare location
//        if (user.getAddress() != null && profile.getAddress() != null &&
//                user.getAddress().getCity().equals(profile.getAddress().getCity())) {
//            score += 0.5; // Increase the score if the cities match
//        }
        // Adjust the overall score based on satisfaction rate
        score *= satisfactionRate;

        return score;
    }

}
