package uk.gov.justice.event.tool.task;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.verify;

import uk.gov.justice.services.eventsourcing.source.core.exception.EventStreamException;
import uk.gov.justice.tools.eventsourcing.transformation.service.EventStreamTransformationService;

import java.util.UUID;

import javax.enterprise.concurrent.ManagedTaskListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StreamTransformationTaskTest {

    private static final UUID STREAM_ID = UUID.randomUUID();

    @Mock
    private EventStreamTransformationService eventStreamTransformationService;

    @Mock
    private ManagedTaskListener transformationListener;

    @InjectMocks
    private StreamTransformationTask streamTransformationTask;

    @Before
    public void setup() {
        streamTransformationTask = new StreamTransformationTask(STREAM_ID, eventStreamTransformationService, transformationListener);
    }

    @Test
    public void shouldTransformAnEvent() throws EventStreamException {
        streamTransformationTask.call();
        verify(eventStreamTransformationService).transformEventStream(STREAM_ID);
    }

    @Test
    public void shouldReturnTransformationListener() {
        assertThat(streamTransformationTask.getManagedTaskListener(), is(transformationListener));
    }

}