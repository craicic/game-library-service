package org.motoc.gamelibrary.business;

import org.motoc.gamelibrary.business.refactor.SimpleCrudMethodsImpl;
import org.motoc.gamelibrary.dto.GameNameDto;
import org.motoc.gamelibrary.model.*;
import org.motoc.gamelibrary.repository.criteria.GameRepositoryCustom;
import org.motoc.gamelibrary.repository.jpa.*;
import org.motoc.gamelibrary.technical.exception.ChildAndParentException;
import org.motoc.gamelibrary.technical.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Perform business logic on the entity Game
 *
 * @author RouzicJ
 */
@Service
@Transactional
public class GameService extends SimpleCrudMethodsImpl<Game, JpaRepository<Game, Long>> {

    private static final Logger logger = LoggerFactory.getLogger(GameService.class);

    private final GameRepository gameRepository;

    private final GameRepositoryCustom gameRepositoryCustom;

    private final CategoryRepository categoryRepository;
    private final ThemeRepository themeRepository;
    private final GameCopyRepository gameCopyRepository;
    private final CreatorRepository creatorRepository;
    private final PublisherRepository publisherRepository;

    @Autowired
    public GameService(JpaRepository<Game, Long> genericRepository,
                       GameRepository gameRepository,
                       GameRepositoryCustom gameRepositoryCustom,
                       CategoryRepository categoryRepository,
                       ThemeRepository themeRepository,
                       GameCopyRepository gameCopyRepository,
                       CreatorRepository creatorRepository,
                       PublisherRepository publisherRepository) {
        super(genericRepository, Game.class);
        this.gameRepository = gameRepository;
        this.gameRepositoryCustom = gameRepositoryCustom;
        this.categoryRepository = categoryRepository;
        this.themeRepository = themeRepository;
        this.gameCopyRepository = gameCopyRepository;
        this.creatorRepository = creatorRepository;
        this.publisherRepository = publisherRepository;
    }


    public List<GameNameDto> findNames() {
        return gameRepositoryCustom.findNames();
    }

    public Page<Game> findPagedOverview(Pageable pageable, String keyword) {
        return gameRepository.findAllByLowerCaseNameContaining(keyword, pageable);
    }

    public Page<Game> findPagedOverview(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public Game addExpansion(Long gameId, List<Long> expansionIds) {
        return gameRepository.findById(gameId)
                .map(game -> {
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The candidate core game "
                                + game.getName() + "  is already set has an expansion of the game : "
                                + game.getCoreGame().getName());

                    List<Game> expansions = gameRepository.findAllById(expansionIds);

                    for (Game expansion : expansions) {
                        /* if a candidate expansion is already an expansion, throw exception */
                        if (expansion.getCoreGame() != null)
                            throw new ChildAndParentException("The candidate expansion : " + expansion
                                    + " is already an expansion of the other game : " + expansion.getCoreGame().getName());
                        /* if a candidate expansion already has an expansion, throw exception */
                        if (!expansion.getExpansions().isEmpty())
                            throw new ChildAndParentException("The candidate expansion : " + expansion.getName()
                                    + " has already at least one expansion : expansion.size()=" + expansion.getExpansions().size());
                    }

                    return gameRepositoryCustom.addExpansions(game, expansions);
                })
                .orElseThrow(
                        () -> {
                            logger.debug("No game of id={} found.", gameId);
                            throw new NotFoundException("No category of id=" + gameId + " found.");
                        }
                );
    }

    public Game addCoreGame(Long gameId, Long coreGameId) {
        Game coreGame = gameRepository.findById(coreGameId)
                .orElseThrow(() -> {
                    throw new NotFoundException("No core game of id=" + coreGameId + " found.");
                });

        return gameRepository.findById(gameId)
                .map(game -> {
                    if (coreGame.getExpansions().contains(game))
                        throw new ChildAndParentException("Core game : " + coreGame.getName() + " is already the core game of " + game.getName());
                    if (game.getCoreGame() != null)
                        throw new ChildAndParentException("The game of id=" + gameId + " already has a core game");
                    if (!game.getExpansions().isEmpty())
                        throw new ChildAndParentException("Game " + game.getName() + " has at least one expansion : it can't have a core game");
                    return gameRepositoryCustom.addCoreGame(game, coreGame);
                })
                .orElseThrow(
                        () -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        });
    }

    public void removeCoreGame(Long id) {
        gameRepository.findById(id).ifPresentOrElse(
                game -> {
                    if (game.getCoreGame() != null)
                        gameRepositoryCustom.removeCoreGame(game);
                    else
                        logger.info("Game of id {} has no core game", id);
                },
                () -> {
                    throw new NotFoundException("No game of id=" + id + " found.");
                });
    }

    public void removeExpansion(Long gameId, Long expansionId) {
        Game game = new Game();
        game.setId(gameId);

        Game expansion = new Game();
        expansion.setId(expansionId);

        gameRepositoryCustom.removeExpansion(game, expansion);
    }

    public Game addCategory(Long gameId, Long categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No category of id=" + categoryId + " found.");
                        }
                );

        return gameRepository
                .findById(gameId)
                .map(game -> gameRepositoryCustom.addCategory(game, category))
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removeCategory(Long gameId, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> {
                    throw new NotFoundException("No category of id={}" + categoryId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> gameRepositoryCustom.removeCategory(game, category), () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

    public Game addTheme(Long gameId, Long themeId) {
        Theme theme = themeRepository
                .findById(themeId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Theme of id=" + themeId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> gameRepositoryCustom.addTheme(game, theme))
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removeTheme(Long gameId, Long themeId) {
        Theme theme = themeRepository.findById(themeId).orElseThrow(() -> {
                    throw new NotFoundException("No theme of id={}" + themeId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> gameRepositoryCustom.removeTheme(game, theme), () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

    public Game addGameCopy(Long gameId, Long gameCopyId) {
        GameCopy gameCopy = gameCopyRepository
                .findById(gameCopyId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No GameCopy of id=" + gameCopyId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> gameRepositoryCustom.addGameCopy(game, gameCopy))
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removeGameCopy(Long gameId, Long gameCopyId) {
        GameCopy gameCopy = gameCopyRepository.findById(gameCopyId).orElseThrow(() -> {
                    throw new NotFoundException("No gameCopy of id={}" + gameCopyId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> gameRepositoryCustom.removeGameCopy(game, gameCopy), () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

    public Game addCreator(Long gameId, Long creatorId) {
        Creator creator = creatorRepository
                .findById(creatorId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Creator of id=" + creatorId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> gameRepositoryCustom.addCreator(game, creator))
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removeCreator(Long gameId, Long creatorId) {
        Creator creator = creatorRepository.findById(creatorId).orElseThrow(() -> {
                    throw new NotFoundException("No creator of id={}" + creatorId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> gameRepositoryCustom.removeCreator(game, creator), () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

    public Game addPublisher(Long gameId, Long publisherId) {
        Publisher publisher = publisherRepository
                .findById(publisherId)
                .orElseThrow(() -> {
                            throw new NotFoundException("No Publisher of id=" + publisherId + " found.");
                        }
                );
        return gameRepository
                .findById(gameId)
                .map(game -> gameRepositoryCustom.addPublisher(game, publisher))
                .orElseThrow(() -> {
                            throw new NotFoundException("No game of id=" + gameId + " found.");
                        }
                );
    }

    public void removePublisher(Long gameId, Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId).orElseThrow(() -> {
                    throw new NotFoundException("No publisher of id={}" + publisherId + " found.");
                }
        );

        gameRepository
                .findById(gameId)
                .ifPresentOrElse(game -> gameRepositoryCustom.removePublisher(game, publisher), () -> {
                    throw new NotFoundException("No game of id=" + gameId + " found.");
                });
    }

//    public Game addCategoryOld(Long gameId, Long categoryId) {
//
//        return gameRepository
//                .findById(gameId)
//                .map(game -> gameRepositoryCustom.addCategoryOld(game, categoryId))
//                .orElseThrow(
//                        () -> {
//                            logger.debug("No game of id={} found.", gameId);
//                            throw new NotFoundException(gameId);
//                        }
//                );
//    }

//    public void removeCategoryOld(Long gameId, Long categoryId) {
//        gameRepository
//                .findById(gameId).ifPresentOrElse(
//                game -> gameRepositoryCustom.removeCategory(game, categoryId)
//                ,
//                () -> {
//                    logger.debug("No game of id={} found.", gameId);
//                    throw new NotFoundException(gameId);
//                }
//        );
//    }
}
