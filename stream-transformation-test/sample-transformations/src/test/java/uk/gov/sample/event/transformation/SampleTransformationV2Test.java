package uk.gov.sample.event.transformation;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static uk.gov.justice.tools.eventsourcing.transformation.api.TransformAction.TRANSFORM_EVENT;

import uk.gov.justice.services.messaging.JsonEnvelope;
import uk.gov.justice.services.messaging.Metadata;
import uk.gov.justice.tools.eventsourcing.transformation.api.EventTransformation;
import uk.gov.justice.tools.eventsourcing.transformation.api.TransformAction;

import java.util.stream.Stream;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SampleTransformationV2Test {

    private SampleTransformationV2 sampleTransformation = new SampleTransformationV2();

    @Test
    public void shouldCreateInstanceOfEventTransformation() {
        assertTrue(sampleTransformation instanceof EventTransformation);
    }

    @Test
    public void shouldSetIsApplicable() {
        JsonEnvelope event = mock(JsonEnvelope.class);
        Metadata metadata = mock(Metadata.class);

        when(event.metadata()).thenReturn(metadata);
        when(event.metadata().name()).thenReturn("sample.v2.events.name");

        assertTrue(sampleTransformation.action(event)== TRANSFORM_EVENT);
    }


    @Test
    public void shouldCreateTransformation() {
        SampleTransformation sampleTransformation = mock(SampleTransformation.class);
        JsonEnvelope event = mock(JsonEnvelope.class);

        when(sampleTransformation.apply(event)).thenReturn(Stream.of(event));

        assertTrue(EqualsBuilder.reflectionEquals(event, sampleTransformation.apply(event).findFirst().get()));
    }


}