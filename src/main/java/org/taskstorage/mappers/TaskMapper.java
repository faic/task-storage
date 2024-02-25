package org.taskstorage.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.taskstorage.dtos.TaskDTO;
import org.taskstorage.persistence.Task;

@Mapper
public interface TaskMapper {
    public static final TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDTO toTaskDTO(Task task);
}
