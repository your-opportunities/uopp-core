package ed.uopp.uoppcore.service.impl;

import ed.uopp.uoppcore.entity.Format;
import ed.uopp.uoppcore.repository.FormatRepository;
import ed.uopp.uoppcore.service.FormatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Objects.requireNonNull;

@Slf4j
@RequiredArgsConstructor
@Service
public class DefaultFormatService implements FormatService {

    public static final String NO_FORMAT_WITH_NAME_FOUND = "No format with name %s found";
    private final FormatRepository formatRepository;

    @Override
    public Format getFormatByName(String formatName) {
        requireNonNull(formatName);

        return formatRepository.findByName(formatName).orElseThrow(
                () -> new NoSuchElementException(String.format(NO_FORMAT_WITH_NAME_FOUND, formatName))
        );
    }

}
